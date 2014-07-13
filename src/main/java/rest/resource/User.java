package rest.resource;

/**
 * Created by Admin on 10.07.2014.
 */
/*@Component
public class UserResource extends ResourceAssemblerSupport<User, UserResource> {
    public UserResource() {
        super(UserQueriesController.class, User.class);
    }

    @Override
    public User toResource(Author author) {
        // will add also a link with rel self pointing itself
        AuthorResource resource = createResourceWithId(author.getAuthorId(), author);
        // adding a link with rel books pointing to the author's books
        resource.add(linkTo(methodOn(AuthorController.class).getAuthorBooks(author.getAuthorId())).withRel("books"));
        return resource;
    }

    @Override
    protected AuthorResource instantiateResource(Author author) {
        return new AuthorResource(author.getAuthorId(), author.getName());
    }
}*/