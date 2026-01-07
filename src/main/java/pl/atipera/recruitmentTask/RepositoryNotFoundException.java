package pl.atipera.recruitmentTask;

public class RepositoryNotFoundException extends RuntimeException{
    public RepositoryNotFoundException(){
        super("User not found");
    }
}
