package br.com.fiap.productbatch.logistic.domain;

public class PostalCode {

  private String state;
  private String locality;
  private String postalCodeRange;

  public PostalCode() {
  }

  public PostalCode(String state, String locality, String postalCodeRange) {
    this.state = state;
    this.locality = locality;
    this.postalCodeRange = postalCodeRange;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getLocality() {
    return locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  public String getPostalCodeRange() {
    return postalCodeRange;
  }

  public void setPostalCodeRange(String postalCodeRange) {
    this.postalCodeRange = postalCodeRange;
  }
}
