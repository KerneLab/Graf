package org.kernelab.graf.table;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import org.kernelab.numeric.matrix.Matrix;

public class MatrixTableModel<E> extends AbstractTableModel
{

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 994405961176885179L;

	private Vector<Object>			header;

	private Matrix<E>				data;

	private Map<Integer, Integer>	rowMap;

	private Map<Integer, Integer>	columnMap;

	private Set<Integer>			notEditableColumns;

	private Set<Integer>			notEditableRows;

	public MatrixTableModel(Vector<Object> header, Matrix<E> data)
	{
		this.header = new Vector<Object>(header);

		this.data = data;

		this.rowMap = new Hashtable<Integer, Integer>();

		this.columnMap = new Hashtable<Integer, Integer>();

		this.notEditableColumns = new HashSet<Integer>();

		this.notEditableRows = new HashSet<Integer>();
	}

	@Override
	public Class<?> getColumnClass(int column)
	{
		Class<?> cls = Object.class;

		Object object = this.getValueAt(0, column);

		if (object != null) {
			cls = object.getClass();
		}

		return cls;
	}

	@Override
	public int getColumnCount()
	{
		return this.getData().getColumns();
	}

	public Map<Integer, Integer> getColumnMap()
	{
		return columnMap;
	}

	@Override
	public String getColumnName(int column)
	{
		return this.getHeader().get(column).toString();
	}

	public Matrix<E> getData()
	{
		return data;
	}

	public Vector<Object> getHeader()
	{
		return header;
	}

	public Set<Integer> getNotEditableColumns()
	{
		return notEditableColumns;
	}

	public Set<Integer> getNotEditableRows()
	{
		return notEditableRows;
	}

	@Override
	public int getRowCount()
	{
		return this.getData().getRows();
	}

	public Map<Integer, Integer> getRowMap()
	{
		return rowMap;
	}

	@Override
	public Object getValueAt(int row, int column)
	{
		Object value = null;

		if (this.hasRowIndex(row) && this.hasColumnIndex(column)) {
			value = this.getData().get(row, column);
		}

		return value;
	}

	public boolean hasColumnIndex(int index)
	{
		return index > -1 && index < this.getColumnCount();
	}

	public boolean hasRowIndex(int index)
	{
		return index > -1 && index < this.getRowCount();
	}

	@Override
	public boolean isCellEditable(int row, int column)
	{
		return !(this.getNotEditableColumns().contains(column) || this
				.getNotEditableRows().contains(row));
	}

	protected void setData(Matrix<E> data)
	{
		this.data = data;
	}

	protected void setHeader(Vector<Object> header)
	{
		this.header = header;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object element, int row, int column)
	{
		if (this.hasRowIndex(row) && this.hasColumnIndex(column)) {
			this.getData().set((E) element, row, column);
		}
	}
}
