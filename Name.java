
public class Name extends genName {
    
    public Name() {
        super();
    }
    
    public Name(Name n) {
        super(n);
    }
    
    public Name(String first, String last) {
        super(first, last);
    }

    public Name getNameCopy() {
        Name copy = new Name(firstName, lastName);
        return copy;
    }
    
    public boolean equals(Name n) {
        if (firstName.equals(n.firstName) && lastName.equals(n.lastName)) {
            return true;
        } else {
            return false;
        }
    }
    
    public String toString() {
        String str = String.format ("\nLast Name: %s"
                + "\nFirst Name: %s", lastName, firstName);
        return str;
    }
    
    public void setFirstName(String first) {
        firstName = first;
    }
    
    public void setLastName(String last) {
        lastName = last;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
}
