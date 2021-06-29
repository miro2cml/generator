package ch.ost.rj.sa.miro2cml.business_logic;

import ch.ost.rj.sa.miro2cml.business_logic.model.BoardRepresentation;
import ch.ost.rj.sa.miro2cml.data_access.MiroApiServiceAdapter;
import ch.ost.rj.sa.miro2cml.data_access.model.miro2cml.BoardMetaData;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class BoardRepresentationProvider {

    @Autowired
    MiroApiServiceAdapter miroApiServiceAdapter;

    public ImmutableTriple <Boolean,Boolean,List<BoardRepresentation>>  getBoards (String accessToken, String teamId){
        ArrayList<BoardRepresentation> output= new ArrayList<>();

        ImmutableTriple <Boolean, Boolean, List<BoardMetaData>> input  = miroApiServiceAdapter.getMiroBoards(accessToken,teamId);
        for (BoardMetaData board: input.getRight()) {
            output.add(new BoardRepresentation(board));
        }

        return new ImmutableTriple <>(input.getLeft(),input.getMiddle(),output);
    }
}
