package practicas.carrito.utils;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemList implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Item> items;

	public ItemList() {

	}

	public ItemList(ArrayList<Item> items) {
		this.items = items;
	}

	public ArrayList<Item> getList() {
		return items;
	}

	public void setList(ArrayList<Item> items) {
		this.items = items;
	}
}
