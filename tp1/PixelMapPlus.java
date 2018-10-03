import java.awt.PageAttributes.ColorType;

/**
 * Classe PixelMapPlus
 * Image de type noir et blanc, tons de gris ou couleurs
 * Peut lire et ecrire des fichiers PNM
 * Implemente les methodes de ImageOperations
 * @author : 
 * @date   : 
 */

public class PixelMapPlus extends PixelMap implements ImageOperations 
{
	/**
	 * Constructeur creant l'image a partir d'un fichier
	 * @param fileName : Nom du fichier image
	 */
	PixelMapPlus(String fileName)
	{
		super( fileName );
	}
	
	/**
	 * Constructeur copie
	 * @param type : type de l'image a creer (BW/Gray/Color)
	 * @param image : source
	 */
	PixelMapPlus(PixelMap image)
	{
		super(image); 
	}
	
	/**
	 * Constructeur copie (sert a changer de format)
	 * @param type : type de l'image a creer (BW/Gray/Color)
	 * @param image : source
	 */
	PixelMapPlus(ImageType type, PixelMap image)
	{
		super(type, image); 
	}
	
	/**
	 * Constructeur servant a allouer la memoire de l'image
	 * @param type : type d'image (BW/Gray/Color)
	 * @param h : hauteur (height) de l'image 
	 * @param w : largeur (width) de l'image
	 */
	PixelMapPlus(ImageType type, int h, int w)
	{
		super(type, h, w);
	}
	
	/**
	 * Genere le negatif d'une image
	 */
	public void negate()
	{
		for (int i = 0 ; i < this.height; i++ ) {
			for (int j = 0 ; j < this.width; j++) {
				imageData[i][j] = imageData[i][j].Negative();
			}
		}
	}
	
	/**
	 * Convertit l'image vers une image en noir et blanc
	 */
	public void convertToBWImage()
	{
		for (int i = 0 ; i < this.height; i++ ) {
			for (int j = 0 ; j < this.width; j++) {
				imageData[i][j] = imageData[i][j].toBWPixel();
			}
		}
		
	}
	
	/**
	 * Convertit l'image vers un format de tons de gris
	 */
	public void convertToGrayImage()
	{
		for (int i = 0 ; i < this.height; i++ ) {
			for (int j = 0 ; j < this.width; j++) {
				imageData[i][j] = imageData[i][j].toGrayPixel();
			}
		}
	}
	
	/**
	 * Convertit l'image vers une image en couleurs
	 */
	public void convertToColorImage()
	{
		for (int i = 0 ; i < this.height; i++ ) {
			for (int j = 0 ; j < this.width; j++) {
				imageData[i][j] = imageData[i][j].toColorPixel();
			}
		}
	}
	
	public void convertToTransparentImage()
	{
		for (int i = 0 ; i < this.height; i++ ) {
			for (int j = 0 ; j < this.width; j++) {
				imageData[i][j] = imageData[i][j].toTransparentPixel();
			}
		}
	}
	
	
	/**
	 * Modifie la longueur et la largeur de l'image 
	 * @param w : nouvelle largeur
	 * @param h : nouvelle hauteur
	 */
	public void resize(int w, int h) throws IllegalArgumentException
	{
		// Credits to Helene Jiang for this whole method
		if(w < 0 || h < 0)
			throw new IllegalArgumentException();
		
		AbstractPixel[][] originalImageData = new AbstractPixel[height][width];
		originalImageData = imageData;

		float ratioHeight = (float)h/height;
		float ratioWidth = (float)w/width;
		int originalHeight = height;
		int originalWidth = width;
		height = h;
		width = w;
		AllocateMemory(imageType, height, width);
		
		for(int row = 0; (row < height) &&  Math.round(row/ratioHeight) < originalHeight ; row++) {
			for(int col = 0; (col < width) && Math.round(col/ratioWidth) < originalWidth; col++) {
				imageData[row][col] = originalImageData[Math.round(row/ratioHeight)][Math.round(col/ratioWidth)];
			}
		}
	}
	
	/**
	 * Insert pm dans l'image a la position row0 col0
	 */
	public void inset(PixelMap pm, int row0, int col0)
	{
		for (int i = 0; i < this.height && i < pm.height  ; i++) {
			for (int j = 0; j < this.width && j < pm.width ; j++) {
				imageData[row0+i][col0+j] = pm.getPixel(i,j);
			}
		}
	}
	
	/**
	 * Decoupe l'image 
	 */
	public void crop(int h, int w)
	{		
		// Copie de l'image originale pour ensuite la manipuler sans changer l'image de base
		AbstractPixel[][] originalImageData = new AbstractPixel[this.height][this.width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0 ; j < this.width ; j++) {
				originalImageData[i][j] = this.imageData[i][j];
			}
		}
		clearData();
		
		this.imageData = new AbstractPixel[h][w];
		for (int i = 0; i < h; i++) {
			for (int j = 0 ; j < w ; j++) {
				imageData[i][j] = originalImageData[i][j];
			}
		}
		
		this.height = h;
		this.width = w;
	}
	
	/**
	 * Effectue une translation de l'image 
	 */
	public void translate(int rowOffset, int colOffset)
	{
		
		AbstractPixel[][] newImageData = new AbstractPixel[height][width];
		
		for (int i = 0; i < this.height; i++) {
			for (int j = 0 ; j < this.width ; j++) {
				if (i-rowOffset < 0 || i-rowOffset >= this.width || j-colOffset < 0 || j-rowOffset >= this.height) {
					newImageData[i][j] = new BWPixel(true);
				}
				else {
					newImageData[i][j] = imageData[i-rowOffset][j-colOffset];
				}
			}
		}
		
		this.imageData = newImageData;
		
	}
	
}
