package entities;

public record Name(String value) {

    public Name {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (value.length() < 2) {
            throw new IllegalArgumentException("Name must have at least 2 characters");
        }

    }

   public String getValue(){
        return value;
   }
}
