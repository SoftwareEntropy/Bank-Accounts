
public abstract class genName {
    protected String firstName;
    protected String lastName;
    
    public genName() {
        firstName = "DefaultFirst";
        lastName = "DefaultLast";
    }
    
    public genName(Name n) {
        firstName = n.firstName;
        lastName = n.lastName;
    }
    
    public genName(String first, String last) {
        firstName = first;
        lastName = last;
    }

    public abstract Name getNameCopy();
    
    public abstract boolean equals(Name n);
    
    @Override
    public String toString() {
        String str = String.format ("\nLast Name: %s"
                + "\nFirst Name: %s", lastName, firstName);
        return str;
    }
    
    public abstract void setFirstName(String first);
    
    public abstract void setLastName(String last);
    
    public abstract String getFirstName();
    
    public abstract String getLastName();
}
