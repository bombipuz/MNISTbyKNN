package fintask.dataset;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

// image & label files:
// 32bit magic number; 32 bit  number of items;
// label: unsigned byte label;
// image: 32 bit rows num; 32 bit column num;

public class MNISTset extends Dataset {

    private static final int ImgsMagicNum=2051;
    private static final int LblsMagicNum=2049;

    public Dataset parser(final InputStream... inputStream) throws IOException{
        //Parse labels
        // from the binary file
        final DataInputStream LblsStream = new DataInputStream(inputStream[0]);
        final int lMagic = LblsStream.readInt(); //read magic num
        if (lMagic != LblsMagicNum)
            throw new IllegalStateException("Wrong label magic number");
        final int NumberOfItems = LblsStream.readInt(); //read number of items
        final int[] labels = new int[NumberOfItems];
        for (int i = 0; i < NumberOfItems; i++)
            labels[i] = LblsStream.readUnsignedByte();  //extract labels
        //Parse images from the binary file
        final DataInputStream ImgsStream = new DataInputStream(inputStream[1]);
        final int iMagic = ImgsStream.readInt(); //read magic num
        if (iMagic != ImgsMagicNum)
            throw new IllegalStateException("Wrong image magic number");
        if (NumberOfItems != ImgsStream.readInt())   //read & compare number of images vs number of labels
            throw new IllegalStateException("Number of Labels and Images items must matched!");
        final int rows = ImgsStream.readInt(); //number of rows 28
        final int cols = ImgsStream.readInt(); //and columns 28
        final double[][] DatasetItems = new double[NumberOfItems][rows * cols]; //784
        for (int i = 0; i < NumberOfItems; i++)
            for (int row = 0; row < rows; row++)
                for (int col = 0; col < cols; col++)
                    DatasetItems[i][row * rows + col] = ImgsStream.readUnsignedByte()/255d; //extract images
        // normalizing values 0..255

        for (int i = 0; i < NumberOfItems; i++)
            addItem(DatasetItems[i], String.valueOf(labels[i])); //fit Dataset obj
        return this;
    }

}
