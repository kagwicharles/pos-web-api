package com.kagwi;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SaleModel {

	public int noOfItems;

	public Double totalPaid;

	public String servedBy;

	public SaleModel() {

	}

}
