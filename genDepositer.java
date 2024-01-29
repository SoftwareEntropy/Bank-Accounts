
public abstract class genDepositer {
    protected Name name;
    protected String socialSecurityNum;
    
    public genDepositer() {
        name = new Name();
        socialSecurityNum = "";
    }
    
    public genDepositer (Depositer d) {
        name = new Name (d.name);
        socialSecurityNum = d.socialSecurityNum;
    }
    
    public genDepositer(Name n, String s) {
        name = n;
        socialSecurityNum = s;
    }
    
    public abstract Depositer getDepositerCopy();
    
    public abstract boolean equals(Depositer d);
    
    public String toString() {
        Name n = name.getNameCopy();
        String first = n.getFirstName();
        String last = n.getLastName();
        String str = String.format("\nLast Name: %s"
                + "\nFirst Name: %s"
                + "\nSocial Security Number: %s", last, first, socialSecurityNum);
        return str;
    }
    
    public abstract Name getName();
    
    public abstract Name getNameCopy();
    
    public abstract String getSSN();
}
