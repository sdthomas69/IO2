package cev.blackFish

class RemoteAddressKey implements Serializable {
    
    String itemKey
    
    boolean equals(other) {
        itemKey.equals(other.itemKey)
    }
    
    int hashCode() {
        itemKey.hashCode()
    }
}
