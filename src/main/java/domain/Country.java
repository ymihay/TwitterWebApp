package domain;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 16.03.14
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class Country {
    private Integer countryId;
    private String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public Country(Integer countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }

        Country country = (Country) o;

        if (!countryName.equals(country.countryName)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return countryName.hashCode();
    }
}
