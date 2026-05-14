package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.BookDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import br.edu.christus.bibliotecapublicavirtual.repository.BookRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import io.minio.Http;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    public BookDTO save(BookDTO bookDTO, MultipartFile file) {
        if (bookDTO.getTitle().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Título não pode exceder 255 caracteres");
        }
        if (bookDTO.getAnoLancamento() > Year.now().getValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ano de lançamento não pode ser maior que o atual.");
        }
        Optional<Book> existingByTitle = repository.findByTitle(bookDTO.getTitle());
        if (existingByTitle.isPresent()
                && (bookDTO.getIsbn() == null || !existingByTitle.get().getIsbn().equals(bookDTO.getIsbn()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este título já está sendo utilizado.");
        }

        String fileName = uploadPdfToMinio((MultipartFile) file);

        Book bookToSave = MapperUtil.parseObject(bookDTO, Book.class);
        bookToSave.setPdfKey(fileName);

        Book savedBook = repository.save(bookToSave);
        return MapperUtil.parseObject(savedBook, BookDTO.class);
    }

    private String uploadPdfToMinio(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "O arquivo PDF é obrigatório.");
        }
        try {

            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), (long) -1)
                            .contentType(file.getContentType())
                            .build());

            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao fazer upload do arquivo para o MinIO.");
        }
    }

    public String getDownloadUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Http.Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(1, TimeUnit.HOURS)
                            .build());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao gerar link de acesso do PDF.");
        }
    }

    public List<BookDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), BookDTO.class);
    }

    public BookDTO findByIsbn(Long isbn) {
        var book = repository.findById(isbn);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe livro com esse ISBN");
        }
        return MapperUtil.parseObject(book.get(), BookDTO.class);
    }

    public void delete(Long isbn) {
        var book = repository.findById(isbn);
        if (book.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe livro com esse ISBN");
        }
        repository.deleteById(isbn);
    }
}
