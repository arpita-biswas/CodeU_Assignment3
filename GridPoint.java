
/**
 * Implemented GridPoint class to represent a cell in the grid.
 * It consists of a row and column number specifying  location in the grid.
 *
 */
public class GridPoint {
	int r;
	int c;
	public GridPoint(int r, int c){
		this.r = r;
		this.c = c;
	}
	
	@Override
	public boolean equals(Object o){
		GridPoint g = (GridPoint)o;
		if(this.r == g.r && this.c == g.c){
			return true;
		}
		return false;		
	}
	
	@Override
	public String toString(){
		return "("+this.r+","+this.c+")";		
	}
}
