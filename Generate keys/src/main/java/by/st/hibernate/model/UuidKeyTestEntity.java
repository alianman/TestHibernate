package by.st.hibernate.model;

import java.io.Serializable;

/**
 * Created by Administrator on 20.01.16.
 */
public class UuidKeyTestEntity implements Serializable {
    private String id;
    private String name;
    private String address;

    public UuidKeyTestEntity(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public UuidKeyTestEntity(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public UuidKeyTestEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(o instanceof UuidKeyTestEntity)) return false;

        UuidKeyTestEntity that = (UuidKeyTestEntity) o;

        if (!getId().equals(that.getId())) return false;
        if (!getName().equals(that.getName())) return false;
        return getAddress().equals(that.getAddress());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getAddress().hashCode();
        return result;
    }
}
