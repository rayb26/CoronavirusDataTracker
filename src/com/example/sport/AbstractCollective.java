package com.example.sport;

//abstract class so class can be subclassed
public abstract class AbstractCollective {
    public enum States{
        //parameter is "real name" of state, which can be fixed onto the url of Worldometer.com. This parameter is not case sensitive
        //All 50 states are included. Some states are not supported by Worldometer.info, but a 404 error validation exists in each regular class
        Alabama("Alabama"),Alaska("Alaska"),Arizona("Arizona"),Arkansas("Arkansas"),California("California"),Colorado("Colorado"),Connecticut("Connecticut"),
        Delaware("Delaware"),Florida("Florida"),Georgia("Georgia"),Hawaii("Hawaii"),Idaho("Idaho"),Illinois("Illinois"), Indiana("Indiana"),Iowa("Iowa"),Kansas("Kansas"),Kentucky("Kentucky"),Louisiana("Louisiana"),Maine("Maine"),
        Maryland("Maryland"),Massachusetts("Massachusetts"),Michigan("Michigan"),Minnesota("Minnesota"),Mississippi("Mississippi"),Missouri("Missouri"),Montana("Montana"),Nebraska("Nebraska"),Nevada("Nevada"),NewHampshire("new-hampshire"),NewJersey("new-jersey"),
        NewMexico("new-mexico"),NewYork("new-york"),NorthCarolina("north-carolina"),NorthDakota("north-dakota"),Ohio("Ohio"),Oklahoma("Oklahoma"),Oregon("Oregon"),
        Pennsylvania("Pennsylvania"),RhodeIsland("rhode-island"),SouthCarolina("south-carolina"), SouthDakota("south-dakota"),Tennessee("Tennessee"),
        Texas("Texas"),Utah("Utah"),Vermont("Vermont"),Virginia("Virginia"),Washington("Washington"), WestVirginia("west-virginia"),Wisconsin("Wisconsin"), Wyoming("Wyoming");

        private States(String realName) {
            this.realName = realName;
        }

        public String getRealName() {
            return realName;
        }
        private final String realName;
    }
    public enum Countries {
        //parameter is "real name" of country, which can be fixed onto the url of Worldometer.com. This parameter is not case sensitive
        //All countries are supported, but a 404 error validation exists in each regular class
        USA("US"),UnitedStatesOfAmerica("US"),UnitedStates("US"), US("US"), America("US"),Spain("Spain"),Italy("Italy"),UK("UK"),Russia("Russia"),
        France("France"),Germany("Germany"),Brazil("Brazil"),Turkey("Turkey"),Iran("Iran"),
        China("China"),Canada("Canada"),Peru("Peru"),India("India"),Belgium("Belgium"),
        Netherlands("Netherlands"),SaudiArabia("Saudi-Arabia"),Mexico("Mexico"),Switzerland("Switzerland"),
        Ecuador("Ecuador"),Portugal("Portugal"),Pakistan("Pakistan"),Chile("Chile"),
        Sweden("Sweden"),Ireland("Ireland"),Singapore("Singapore"),Belarus("Belarus"),Qatar("Qatar"),
        UAE("UAE"),Israel("Israel"),Austria("Austria"),Japan("Japan"),Poland("Poland"),
        Romania("Romania"),Ukraine("Ukraine"),Bangladesh("Bangladesh"),Indonesia("Indonesia"),
        SouthKorea("south-korea"),Philippines("Philippines"),Denmark("Denmark"),Colombia("Colombia"),
        Serbia("Serbia"),DominicanRepublic("dominican-republic"),SouthAfrica("south-africa"),Egypt("Egypt"),
        Czechia("Czechia"),Panama("Panama"),Norway("Norway"),Kuwait("Kuwait"),
        Australia("Australia"),Malaysia("Malaysia"),Finland("Finland"),Morocco("Morocco"),
        Argentina("Argentina"),Algeria("Algeria"),Kazakhstan("Kazakhstan"),Moldova("Moldova"),
        Bahrain("Bahrain"),Ghana("Ghana"),Nigeria("Nigeria"),Luxembourg("Luxembourg"),
        Afghanistan("Afghanistan"),Hungary("Hungary"),Oman("Oman"),Armenia("Armenia"),
        Thailand("Thailand"),Greece("Greece"),Iraq("Iraq"),
        Uzbekistan("Uzbekistan"),Azerbaijan("Azerbaijan"),Cameroon("Cameroon"),
        Bolivia("Bolivia"),Croatia("Croatia"),BosniaandHerzegovina("bosnia-and-herzegovina"), Bosnia("bosnia-and-herzegovina"),
        Herzegovina("bosnia-and-herzegovina"),
        Guinea("Guinea"),Bulgaria("Bulgaria"),Iceland("Iceland"),Honduras("Honduras"),
        Cuba("Cuba"),
        Estonia("Estonia"),IvoryCoast("Ivory-Coast"),
        NorthMacedonia("North-Macedonia"),Senegal("Senegal"),
        NewZealand("New-Zealand"),Slovakia("Slovakia"),Slovenia("Slovenia"),Lithuania("Lithuania"),
        Djibouti("Djibouti"),Sudan("Sudan"),HongKong("Hong-Kong"),Tunisia("Tunisia"),
        DRC("democratic-republic-of-the-congo"),
        Somalia("Somalia"),Latvia("Latvia"),Kyrgyzstan("Kyrgyzstan"),Cyprus("Cyprus"),
        Mayotte("Mayotte"),Albania("Albania"),SriLanka("Sri-Lanka"),Guatemala("Guatemala"),
        Lebanon("Lebanon"),Niger("Niger"),CostaRica("Costa-Rica"),Andorra("Andorra"),
        BurkinaFaso("Burkina-Faso"),
        Maldives("Maldives"),ElSalvador("El-Salvador"),Uruguay("Uruguay"),Mali("Mali"),
        SanMarino("San-Marino"),Georgia("Georgia"),Kenya("Kenya"),Gabon("Gabon"),
        GuineaBissau("Guinea-Bissau"),Paraguay("Paraguay"),
        ChannelIslands("Channel-Islands"),
        Tajikistan("Tajikistan"),Tanzania("Tanzania"),Jordan("Jordan"),Jamaica("Jamaica"),
        Malta("Malta"),Taiwan("Taiwan"),
        EquatorialGuinea("Equatorial-Guinea"),Réunion("Réunion"),Venezuela("Venezuela"),
        Palestine("Palestine"),Mauritius("Mauritius"),
        IsleofMan("Isle-of-Man"),Montenegro("Montenegro"),Vietnam("Vietnam"),Congo("Congo"),Rwanda("Rwanda"),
        Chad("Chad"),SierraLeone("Sierra-Leone"),Benin("Benin"),
        CaboVerde("Cabo-Verde"),SaoTomeandPrincipe("sao-tome-and-principe"),
        Liberia("Liberia"),Ethiopia("Ethiopia"),Madagascar("Madagascar"),FaeroeIslands("Faeroe-Islands"),
        Martinique("Martinique"),Myanmar("Myanmar"),Zambia("Zambia"),Eswatini("Eswatini"),
        Guadeloupe("Guadeloupe"),Haiti("Haiti"),Gibraltar("Gibraltar"),
        Togo("Togo"),CAR("central-african-republic"),Brunei("Brunei"),FrenchGuiana("French-Guiana"),
        Cambodia("Cambodia"),SouthSudan("South-Sudan"),Bermuda("Bermuda"),
        TrinidadandTobago("Trinidad-and-Tobago"),Nepal("Nepal"),Aruba("Aruba"),Uganda("Uganda"),
        Monaco("Monaco"),Guyana("Guyana"),Bahamas("Bahamas"),Barbados("Barbados"),
        Liechtenstein("Liechtenstein"),Mozambique("Mozambique"),CaymanIslands("Cayman-Islands"),
        SintMaarten("Sint-Maarten"),Libya("Libya"),FrenchPolynesia("French-Polynesia"),
        Syria("Syria"),Macao("Macao"),Malawi("Malawi"),Angola("Angola"),
        Mongolia("Mongolia"),Eritrea("Eritrea"),Yemen("Yemen"),
        Zimbabwe("Zimbabwe"),AntiguaandBarbuda("Antigua-and-Barbuda"),TimorLeste("Timor-Leste"),
        Botswana("Botswana"),Grenada("Grenada"),Gambia("Gambia"),Laos("Laos"),
        Belize("Belize"),Fiji("Fiji"),NewCaledonia("New-Caledonia"),SaintLucia("Saint-Lucia"),
        StVincentGrenadines("saint-vincent-and-the-grenadines"),Nicaragua("Nicaragua"),Curaçao("Curaçao"),
        Dominica("Dominica"),Namibia("Namibia"),Burundi("Burundi"),
        SaintKittsandNevis("saint-kitts-and-nevis"),FalklandIslands("Falkland-islands"),
        TurksandCaicos("Turks-and-Caicos"),VaticanCity("Vatican-City"),
        Montserrat("Montserrat"),Greenland("Greenland"),Seychelles("Seychelles"),
        Suriname("Suriname"),Comoros("Comoros"),Mauritania("Mauritania"),
        PapuaNewGuinea("Papua-New-Guinea"),BritishVirginIslands("British-Virgin-Islands"), VirginIslands("British-Virgin-Islands"),
        Bhutan("Bhutan"),CaribbeanNetherlands("Caribbean-Netherlands"),
        StBarth("saint-barthelemy"),WesternSahara("Western-Sahara"),Anguilla("Anguilla"),
        SaintPierreMiquelon("saint-pierre-and-miquelon");


        private Countries(String realName) {
            this.realName = realName;
        }
        public String getRealName() {
            return realName;
        }
        private final String realName;

    }

}
