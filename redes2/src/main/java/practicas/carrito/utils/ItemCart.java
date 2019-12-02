package practicas.carrito.utils;

public class ItemCart {

	private int idItem;
	private int quantity;
	private String name;

	public ItemCart(int idItem, int quantity, String name) {
		this.idItem = idItem;
		this.quantity = quantity;
		this.name = name;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
