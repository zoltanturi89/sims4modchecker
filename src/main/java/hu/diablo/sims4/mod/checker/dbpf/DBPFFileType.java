package hu.diablo.sims4.mod.checker.dbpf;

public enum DBPFFileType {
	_IMG(new String[]{"00B2D882","B6C8B6A0","BA856C78"},"dds"),
	GEOM(new String[]{"015A1849"},"geom"),
	MODL(new String[]{"01661233"},"scene"),
	_AUD(new String[]{"01A527DB","01EEF63A"},"mm"),
	MLOD(new String[]{"01D10F34"},"scene"),
	SIMO(new String[]{"025ED6F4"},"xml"),
	JAZZ(new String[]{"02D5DF13"},"xml"),
	_XML(new String[]{"0333406C","1B25A024","4115F9D5","99D98089",
			"A576C2E7","B61DE6B4","C202C770",
			"C582D2FB","CB5FDDC7","E231B3D8",
			"E882D22F"},"xml"),
	CASP(new String[]{"034AEECB"},"caspart"),
	TONE(new String[]{"0354796A"},"skintone"),
	BOND(new String[]{"0355E0A6"},"bonedelta"),
	LITE(new String[]{"03B4C61D"},"light"),
	BGEO(new String[]{"067CAA11"},"blendgeom"),
	LOOT(new String[]{"0C772E27"},"xml"),
	XML(new String[]{"1A8506C5"},"xml"),
	STBL(new String[]{"220557DA"},"stbl"),
	FONTS(new String[]{"25796DCA","276CA4B9"},"font"),
	CUR(new String[]{"26978421"},"cur"),
	_PNG(new String[]{"2F7D0004","3C1AF1F2",
			"5B282D45","9C925813","CD9DE247"},"png"),
	COBJ(new String[]{"319E4F1D"},"cobj"),
	DDS(new String[]{"3453CF95"},"DDS"),
	_AVI(new String[]{"376840D7"},"avi"),
	DATA(new String[]{"545AC67A"},"data"),
	BUFF(new String[]{"6017E896"},"xml"),
	_GFX(new String[]{"62ECC59A"},"gfx"),
	CLIP(new String[]{"6B20C4F3"},"animation"),
	FASH(new String[]{"71BDB8A2"},"fash"),
	_RIG(new String[]{"8EAF13DE"},"skeleton"),
	LOCANIM(new String[]{"9AFE47F5"},"xml"),
	GEOMREF(new String[]{"AC16FBEC"},"geomref"),
	SIMSAVE(new String[]{"B3C438F0"},"save"),
	AUEV(new String[]{"BDD82221"},"auev"),
	OBJD(new String[]{"C0DB5AE7"},"objd"),
	FTPT(new String[]{"D382BF57"},"scene"),
	UNKN(new String[]{"DB43E069","EAA32ADD"},"unknown"),
	_SWB(new String[]{"EA5118B0"},"unknown"),
	DIR(new String[]{"E86B1EEF"},"dir");
	
	DBPFFileType(String[] typeIds,String extension) {
		this.typeIds = typeIds;
		this.extension = extension;
	}

	public String[] typeIds;
	public String extension;
}
