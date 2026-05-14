package br.edu.christus.bibliotecapublicavirtual.service;

import br.edu.christus.bibliotecapublicavirtual.domain.dto.EditoraDTO;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Editora;
import br.edu.christus.bibliotecapublicavirtual.domain.model.Book;
import br.edu.christus.bibliotecapublicavirtual.repository.EditoraRepository;
import br.edu.christus.bibliotecapublicavirtual.repository.BookRepository;
import br.edu.christus.bibliotecapublicavirtual.utils.MapperUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public EditoraDTO save(EditoraDTO editoraDTO) {
        if (editoraDTO.getName().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome não pode ultrapassar 255 caracteres");
        }

        boolean emailExists;
        if (editoraDTO.getId() == null) {
            emailExists = repository.existsByEmail(editoraDTO.getEmail());
        } else {
            emailExists = repository.existsByEmailAndIdNot(editoraDTO.getEmail(), editoraDTO.getId());
        }

        if (emailExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este e-mail já está sendo utilizado.");
        }

        boolean cnpjExists;
        if (editoraDTO.getId() == null) {
            cnpjExists = repository.existsByCnpj(editoraDTO.getCnpj());
        } else {
            cnpjExists = repository.existsByCnpjAndIdNot(editoraDTO.getCnpj(), editoraDTO.getId());
        }

        if (cnpjExists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Este CNPJ já está sendo utilizado.");
        }

        Editora editoraEntity = MapperUtil.parseObject(editoraDTO, Editora.class);

        if (editoraDTO.getLivrosIds() != null && !editoraDTO.getLivrosIds().isEmpty()) {
            List<Book> livrosGerenciados = editoraDTO.getLivrosIds().stream()
                    .map(id -> bookRepository.findById(id)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    "Livro com ISBN " + id + " não encontrado no sistema.")))
                    .collect(Collectors.toList());

            editoraEntity.setLivros(livrosGerenciados);
        } else {
            editoraEntity.setLivros(null);
        }

        var editoraSaved = repository.save(editoraEntity);

        EditoraDTO responseDTO = MapperUtil.parseObject(editoraSaved, EditoraDTO.class);

        if (editoraSaved.getLivros() != null) {
            List<Long> idsSalvos = editoraSaved.getLivros().stream()
                    .map(Book::getIsbn)
                    .collect(Collectors.toList());
            responseDTO.setLivrosIds(idsSalvos);
        }

        return responseDTO;
    }

    public List<EditoraDTO> findAll() {
        return MapperUtil.parseListObjects(repository.findAll(), EditoraDTO.class);
    }

    public EditoraDTO findById(Long id) {
        var editora = repository.findById(id);
        if (editora.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe editora com esse ID");
        }
        return MapperUtil.parseObject(editora.get(), EditoraDTO.class);
    }

    public void delete(Long id) {
        var editora = repository.findById(id);
        if (editora.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Não existe editora com esse ID");
        }
        repository.deleteById(id);
    }
}