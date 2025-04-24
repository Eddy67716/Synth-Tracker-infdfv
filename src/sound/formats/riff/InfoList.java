/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sound.formats.riff;

import io.IReadable;
import io.IWritable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static sound.formats.riff.RiffChunk.S_GROUP_ID_LENGTH;
import sound.formats.riff.RiffList;
import sound.formats.riff.text.ArchivalLocation;
import sound.formats.riff.text.ArtistText;
import sound.formats.riff.text.CommisionerNameText;
import sound.formats.riff.text.CopyrightText;
import sound.formats.riff.text.CreationDate;
import sound.formats.riff.text.FileEngineer;
import sound.formats.riff.text.FileSubjectNameTitle;
import sound.formats.riff.text.GenralCommentChunk;
import sound.formats.riff.text.Genre;
import sound.formats.riff.text.InfoTextChunk;
import sound.formats.riff.text.Keywords;
import sound.formats.riff.text.Medium;
import sound.formats.riff.text.ProductTitleChunk;
import sound.formats.riff.text.SoftwareaPackageText;
import sound.formats.riff.text.Source;
import sound.formats.riff.text.SourceForm;
import sound.formats.riff.text.Subject;
import sound.formats.riff.text.Technician;

/**
 *
 * @author eddy6
 */
public class InfoList extends RiffList {

    // constant
    public static final String LIST_ID = "INFO";

    // instance variables
    private List<InfoTextChunk> textChunkList;

    public InfoList(long chunkSize) {
        super(LIST_ID, chunkSize);
    }

    public InfoList() {
        super(LIST_ID);
    }
    
    // get by riff type
    public InfoTextChunk getTextChunkByGroupID(String sGroupID) {
        for (InfoTextChunk textChunk : textChunkList) {
            if (textChunk.getsGroupID().equals(sGroupID)) {
                return textChunk;
            }
        }
        
        return null;
    }

    @Override
    public boolean read(IReadable r) throws IOException, FileNotFoundException, IllegalArgumentException {

        boolean read = validateList(r);

        long fileIndex = r.getFilePosition();

        textChunkList = new ArrayList<>();

        while (r.getFilePosition() - fileIndex < this.getChunkSize() - 4) {

            InfoTextChunk chunk;

            String idString = r.getByteString(S_GROUP_ID_LENGTH);

            switch (idString) {
                default:
                    chunk = new InfoTextChunk(idString);
                    break;
                case ArchivalLocation.S_GROUP_ID:
                    chunk = new ArchivalLocation();
                    break;
                case ArtistText.S_GROUP_ID:
                    chunk = new ArtistText();
                    break;
                case CommisionerNameText.S_GROUP_ID:
                    chunk = new CommisionerNameText();
                    break;
                case GenralCommentChunk.S_GROUP_ID:
                    chunk = new GenralCommentChunk();
                    break;
                case CopyrightText.S_GROUP_ID:
                    chunk = new CopyrightText();
                    break;
                case CreationDate.S_GROUP_ID:
                    chunk = new CreationDate();
                    break;
                case FileEngineer.S_GROUP_ID:
                    chunk = new FileEngineer();
                    break;
                case Genre.S_GROUP_ID:
                    chunk = new Genre();
                    break;
                case Keywords.S_GROUP_ID:
                    chunk = new Keywords();
                    break;
                case Medium.S_GROUP_ID:
                    chunk = new Medium();
                    break;
                case FileSubjectNameTitle.S_GROUP_ID:
                    chunk = new FileSubjectNameTitle();
                    break;
                case ProductTitleChunk.S_GROUP_ID:
                    chunk = new ProductTitleChunk();
                    break;
                case Subject.S_GROUP_ID:
                    chunk = new Subject();
                    break;
                case SoftwareaPackageText.S_GROUP_ID:
                    chunk = new SoftwareaPackageText();
                    break;
                case Source.S_GROUP_ID:
                    chunk = new Source();
                    break;
                case SourceForm.S_GROUP_ID:
                    chunk = new SourceForm();
                    break;
                case Technician.S_GROUP_ID:
                    chunk = new Technician();
                    break;
            }

            chunk.setRiffIdRead(true);

            chunk.read(r);

            textChunkList.add(chunk);
        }

        return read;
    }
}
