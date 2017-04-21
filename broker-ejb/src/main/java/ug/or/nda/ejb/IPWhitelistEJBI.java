package ug.or.nda.ejb;

import ug.or.nda.dto.WhitelistRequest;
import ug.or.nda.dto.WhitelistResponse;

public interface IPWhitelistEJBI {

	public WhitelistResponse process(WhitelistRequest req);

}
