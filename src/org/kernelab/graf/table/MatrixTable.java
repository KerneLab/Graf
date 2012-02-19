package org.kernelab.graf.table;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.kernelab.basis.Tools;
import org.kernelab.numeric.matrix.Matrix;
import org.kernelab.numeric.matrix.Range;

/**
 * The GUI component, which extends JScrollPane and implemented as JTable, to
 * display the Matrix.
 * 
 * @author Dilly King
 * 
 * @version 1.1.5
 * @update 2009-07-31
 * 
 * @param <E>
 *            The generic type of elements in the Matrix.
 */
public class MatrixTable<E> implements EssentialTable<Matrix<E>>
{

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3052546166318459520L;

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Double[][] am = new Double[][] { { 1.0, 2.0, 3.0, 7.3 }, { 2.1, 8.7, 5.5, 3.6 },
				{ 6.5, 1.6, 4.7, 8.4 } };

		Matrix<Double> m = new Matrix<Double>(am);

		final MatrixTable<Double> mt = new MatrixTable<Double>(m, "1", "2", "3", "4");

		mt.getTable().setRowSelectionAllowed(true);
		mt.getTable().setColumnSelectionAllowed(true);

		mt.getTable().setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setLayout(new GridLayout(1, 1));
		f.add(new JScrollPane(mt.getTable()));

		f.setPreferredSize(new Dimension(300, 300));

		f.setVisible(true);
	}

	private Vector<Object>		header;

	private Matrix<E>			data;

	private JTable				table;

	private MatrixTableModel<E>	model;

	public MatrixTable(Matrix<E> data, String... header)
	{
		this(data, Tools.vectorOfArray((Object[]) header));
	}

	public MatrixTable(Matrix<E> data, Vector<Object> header)
	{
		this.data = data;
		this.model = new MatrixTableModel<E>(header, data);
		this.table = new JTable(model);
		this.table.getTableHeader().setReorderingAllowed(false);
		this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.table.setColumnSelectionAllowed(true);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	public Vector<Object> getColumnsHeader()
	{
		return header;
	}

	@Override
	public Matrix<E> getData()
	{
		return data;
	}

	@Override
	public MatrixTableModel<E> getModel()
	{
		return model;
	}

	@Override
	public Matrix<E> getSelectedData()
	{
		Matrix<E> selected = null;

		Range range = this.getSelectedRange();

		if (range != null) {
			selected = this.getData().quote(range);
		}

		return selected;
	}

	public Range getSelectedRange()
	{
		int beginRow = -1, endRow = -1;
		int beginColumn = -1, endColumn = -1;

		for (int row : this.getTable().getSelectedRows()) {

			if (row < beginRow || beginRow < 0) {
				beginRow = row;
			}

			if (row > endRow || endRow < 0) {
				endRow = row;
			}
		}

		for (int column : this.getTable().getSelectedColumns()) {

			if (column < beginColumn || beginColumn < 0) {
				beginColumn = column;
			}

			if (column > endColumn || endColumn < 0) {
				endColumn = column;
			}
		}

		Range range = null;

		if (beginRow > -1 && beginColumn > -1) {

			range = new Range(beginRow, endRow, beginColumn, endColumn);
		}

		return range;
	}

	@Override
	public JTable getTable()
	{
		return table;
	}

	@Override
	public void refreshData()
	{
		this.getModel().fireTableDataChanged();
	}

	public void refreshData(int row, int column)
	{
		this.getModel().fireTableCellUpdated(row, column);
	}

	@Override
	public void refreshHeader()
	{
		this.getModel().fireTableStructureChanged();
	}

	public void selectRange(Range range)
	{
		this.getTable().setRowSelectionInterval(range.getBegin().getRow(),
				range.getEnd().getRow());

		this.getTable().setColumnSelectionInterval(range.getBegin().getColumn(),
				range.getEnd().getColumn());
	}

	public void setColumnsHeader(Object... header)
	{
		this.setColumnsHeader(Tools.vectorOfArray(header));
	}

	public void setColumnsHeader(Vector<Object> header)
	{
		this.header = new Vector<Object>(header);
		this.model.setHeader(this.header);
	}

	public void setData(Matrix<E> data)
	{
		this.data = data;
		this.getModel().setData(data);
	}

	protected void setModel(MatrixTableModel<E> model)
	{
		this.model = model;
	}

}
