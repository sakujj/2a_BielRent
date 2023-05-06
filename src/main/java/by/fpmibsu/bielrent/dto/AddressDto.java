package by.fpmibsu.bielrent.dto;

import lombok.Builder;

@Builder
public final class AddressDto {
    private final long id;
    private final int regionNumber;
    private final String city;
    private final String districtAdministrative;
    private final String districtMicro;
    private final String street;
    private final int houseNumber;

    public long getId() {
        return this.id;
    }

    public int getRegionNumber() {
        return this.regionNumber;
    }

    public String getCity() {
        return this.city;
    }

    public String getDistrictAdministrative() {
        return this.districtAdministrative;
    }

    public String getDistrictMicro() {
        return this.districtMicro;
    }

    public String getStreet() {
        return this.street;
    }

    public int getHouseNumber() {
        return this.houseNumber;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AddressDto)) return false;
        final AddressDto other = (AddressDto) o;
        if (this.getId() != other.getId()) return false;
        if (this.getRegionNumber() != other.getRegionNumber()) return false;
        final Object this$city = this.getCity();
        final Object other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false;
        final Object this$districtAdministrative = this.getDistrictAdministrative();
        final Object other$districtAdministrative = other.getDistrictAdministrative();
        if (this$districtAdministrative == null ? other$districtAdministrative != null : !this$districtAdministrative.equals(other$districtAdministrative))
            return false;
        final Object this$districtMicro = this.getDistrictMicro();
        final Object other$districtMicro = other.getDistrictMicro();
        if (this$districtMicro == null ? other$districtMicro != null : !this$districtMicro.equals(other$districtMicro))
            return false;
        final Object this$street = this.getStreet();
        final Object other$street = other.getStreet();
        if (this$street == null ? other$street != null : !this$street.equals(other$street)) return false;
        if (this.getHouseNumber() != other.getHouseNumber()) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $id = this.getId();
        result = result * PRIME + (int) ($id >>> 32 ^ $id);
        result = result * PRIME + this.getRegionNumber();
        final Object $city = this.getCity();
        result = result * PRIME + ($city == null ? 43 : $city.hashCode());
        final Object $districtAdministrative = this.getDistrictAdministrative();
        result = result * PRIME + ($districtAdministrative == null ? 43 : $districtAdministrative.hashCode());
        final Object $districtMicro = this.getDistrictMicro();
        result = result * PRIME + ($districtMicro == null ? 43 : $districtMicro.hashCode());
        final Object $street = this.getStreet();
        result = result * PRIME + ($street == null ? 43 : $street.hashCode());
        result = result * PRIME + this.getHouseNumber();
        return result;
    }

    public String toString() {
        return "AddressDto(id=" + this.getId() + ", regionNumber=" + this.getRegionNumber() + ", city=" + this.getCity() + ", districtAdministrative=" + this.getDistrictAdministrative() + ", districtMicro=" + this.getDistrictMicro() + ", street=" + this.getStreet() + ", houseNumber=" + this.getHouseNumber() + ")";
    }
}
