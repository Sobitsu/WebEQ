/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sobit.webeq.services.data.parameters;

public enum Order {
          ASCENDING("ascending"), 
	  DESCENDING("descending");
	  
	  private String text;

	  Order(String text) {
	    this.text = text;
	  }

	  public String getText() {
	    return this.text;
	  }	
	  
	  public static Order fromString(String text) {
		    if (text != null) {
		      for (Order b : Order.values()) {
		        if (text.equalsIgnoreCase(b.text)) {
		          return b;
		        }
		      }
		    }
		    return null;
	}   
}
