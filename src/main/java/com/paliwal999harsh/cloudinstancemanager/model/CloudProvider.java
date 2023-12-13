package com.paliwal999harsh.cloudinstancemanager.model;

public enum CloudProvider {
    Azure,
    AWS;

    public static Boolean isValid(CloudProvider value) {
        for (CloudProvider b : CloudProvider.values()) {
          if (b.compareTo(value)==0) {
            return true;
          }
        }
        return false;
    }
}
