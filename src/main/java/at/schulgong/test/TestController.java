package at.schulgong.test;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@RestController
public class TestController {

  private final TestRepository repository;

  private final TestModelAssembler assembler;

  public TestController(TestRepository repository, TestModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  /**
   * Method for READ operation
   *
   * @return all entries from the table test
   */
  @GetMapping("/tests")
  CollectionModel<EntityModel<Test>> all() {

    List<EntityModel<Test>> tests = repository.findAll().stream()
      .map(assembler::toModel)
      .collect(Collectors.toList());

    return CollectionModel.of(tests,
      linkTo(methodOn(TestController.class).all()).withSelfRel());
  }


  /**
   * Method for CREATE operation
   *
   * @return new entry for database
   */
  @PostMapping("/tests") // define Post-Route (CREATE-operation)
  ResponseEntity<?> newTest(@RequestBody Test newTest) {
    EntityModel<Test> entityModel = assembler.toModel(repository.save(newTest));

    return ResponseEntity
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(entityModel);
  }


  /**
   * Method for READ operation
   *
   * @return one entry, validated by id
   */
  @GetMapping("/tests/{id}") // define Get-Route for one sensor entry (READ-operation)
  EntityModel<Test> one(@PathVariable Long id) {
    Test song = repository.findById(id)
      .orElseThrow(() -> new TestNotFoundException(id));

    return assembler.toModel(song);
  }

  /**
   * Method for UPDATE operation
   *
   * @return new (updated) entry for database
   */
  @PutMapping("/tests/{id}") // define Put-Route (UPDATE-operation)
  ResponseEntity<?> replaceSensor(@RequestBody Test newTest, @PathVariable Long id) {
    Test updateTest = repository.findById(id)
      .map(test -> {
        test.setInfo(newTest.getInfo());
        return repository.save(test);
      })
      .orElseGet(() -> {

        newTest.setId(id);
        return repository.save(newTest);
      });
    EntityModel<Test> entityModel = assembler.toModel(updateTest);

    return ResponseEntity
      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
      .body(entityModel);
  }

  /**
   * Method for DELETE operation
   *
   * @return new (empty) entry for database
   */
  @DeleteMapping("/tests/{id}") // define Delete-Route (DELETE-operation)
  ResponseEntity<?> deleteTest(@PathVariable Long id) {
    repository.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}
