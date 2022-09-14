@org.hibernate.annotations.GenericGenerator(
    name = "ACTIVITY_SEQ",
    strategy = "enhanced-sequence",
    parameters = {
      @org.hibernate.annotations.Parameter(name = "sequence_name", value = "ACTIVITY_SEQ"),
      @org.hibernate.annotations.Parameter(name = "schema", value = "TEST"),
      @org.hibernate.annotations.Parameter(name = "catalog", value = "TEST"),
      @org.hibernate.annotations.Parameter(name = "allocation_size", value = "50")
    })
package org.datrunk.descent.entities;
