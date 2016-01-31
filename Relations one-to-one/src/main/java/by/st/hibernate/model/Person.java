package by.st.hibernate.model;

/**
 * Created by alian on 31.01.2016.
 */
public class Person {
    private long id;
    private String name;
    private PersonPosition position;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonPosition getPosition() {
        return position;
    }

    public void setPosition(PersonPosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getId() != person.getId()) return false;
        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) return false;
        return !(getPosition() != null ? !getPosition().equals(person.getPosition()) : person.getPosition() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPosition() != null ? getPosition().hashCode() : 0);
        return result;
    }
}
