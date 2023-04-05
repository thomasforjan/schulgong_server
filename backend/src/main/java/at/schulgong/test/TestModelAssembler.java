package at.schulgong.test;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Thomas Forjan, Philipp Wildzeiss, Martin Kral
 * @version 0.1
 * @implNote practical project
 * @since March 2023
 */
@Component
public class TestModelAssembler implements RepresentationModelAssembler<Test, EntityModel<Test>> {

  /**
   * Method to publish the own link of the entry as well
   *
    * @param test object of entry
   * @return self-relation and overall link
   */
  @Override
  public EntityModel<Test> toModel(Test test){
    return EntityModel.of(test,
      linkTo(methodOn(TestController.class).one(test.getId())).withSelfRel(),
      linkTo(methodOn(TestController.class).all()).withRel("tests"));
  }
}
