package practicas.carrito.utils;

import java.io.Serializable;

public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String pic;
	private String name;
	private int stock;
	private int price;

	public Item(int id, String pic, String name, int stock, int price) {
		this.id = id;
		this.pic = pic;
		this.name = name;
		this.stock = stock;
		this.price = price;
	}

	public Item(Item i) {// Solo usar para agregar algo al carrito
		this.id = i.getId();
		this.pic = i.getPic();
		this.name = i.getName();
		this.stock = 1;
		this.price = i.getPrice();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
