package by.st.hibernate.model;

import java.io.Serializable;

/**
 * Created by Administrator on 20.01.16.
 */
public class AssignedKeyTestEntity implements Serializable {
    private long id;
    private String name;
    private String address;

    public AssignedKeyTestEntity(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public AssignedKeyTestEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public AssignedKeyTestEntity() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssignedKeyTestEntity)) return false;

        AssignedKeyTestEntity nativeKeyTestEntity = (AssignedKeyTestEntity) o;

        if (getId() != nativeKeyTestEntity.getId()) return false;
        if (!getName().equals(nativeKeyTestEntity.getName())) return false;
        return getAddress().equals(nativeKeyTestEntity.getAddress());

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getAddress().hashCode();
        return result;
    }
}
