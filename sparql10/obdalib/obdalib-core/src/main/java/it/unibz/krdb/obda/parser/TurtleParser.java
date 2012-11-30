// $ANTLR 3.4 C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g 2012-11-30 10:44:47

package it.unibz.krdb.obda.parser;

import it.unibz.krdb.obda.model.Atom;
import it.unibz.krdb.obda.model.CQIE;
import it.unibz.krdb.obda.model.Function;
import it.unibz.krdb.obda.model.OBDADataFactory;
import it.unibz.krdb.obda.model.OBDALibConstants;
import it.unibz.krdb.obda.model.Predicate;
import it.unibz.krdb.obda.model.Predicate.COL_TYPE;
import it.unibz.krdb.obda.model.NewLiteral;
import it.unibz.krdb.obda.model.URIConstant;
import it.unibz.krdb.obda.model.Variable;
import it.unibz.krdb.obda.model.ValueConstant;
import it.unibz.krdb.obda.model.impl.OBDADataFactoryImpl;
import it.unibz.krdb.obda.model.impl.OBDAVocabulary;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.TokenStream;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class TurtleParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ALPHA", "ALPHANUM", "AMPERSAND", "APOSTROPHE", "ASTERISK", "AT", "BACKSLASH", "BASE", "BLANK", "BLANK_PREFIX", "CARET", "CHAR", "COLON", "COMMA", "DECIMAL", "DECIMAL_NEGATIVE", "DECIMAL_POSITIVE", "DIGIT", "DOLLAR", "DOUBLE", "DOUBLE_NEGATIVE", "DOUBLE_POSITIVE", "DOUBLE_SLASH", "ECHAR", "EQUALS", "EXCLAMATION", "FALSE", "GREATER", "HASH", "ID", "ID_CORE", "ID_START", "INTEGER", "INTEGER_NEGATIVE", "INTEGER_POSITIVE", "LCR_BRACKET", "LESS", "LPAREN", "LSQ_BRACKET", "LTSIGN", "MINUS", "NAMESPACE", "NAME_CHAR", "NAME_START_CHAR", "NCNAME", "PERCENT", "PERIOD", "PLUS", "PREFIX", "PREFIXED_NAME", "QUESTION", "QUOTE_DOUBLE", "QUOTE_SINGLE", "RCR_BRACKET", "REFERENCE", "RPAREN", "RSQ_BRACKET", "RTSIGN", "SCHEMA", "SEMI", "SLASH", "STRING_URI", "STRING_WITH_QUOTE", "STRING_WITH_QUOTE_DOUBLE", "STRING_WITH_TEMPLATE_SIGN", "TILDE", "TRUE", "UNDERSCORE", "URI_PATH", "VARNAME", "WS", "'a'"
    };

    public static final int EOF=-1;
    public static final int T__75=75;
    public static final int ALPHA=4;
    public static final int ALPHANUM=5;
    public static final int AMPERSAND=6;
    public static final int APOSTROPHE=7;
    public static final int ASTERISK=8;
    public static final int AT=9;
    public static final int BACKSLASH=10;
    public static final int BASE=11;
    public static final int BLANK=12;
    public static final int BLANK_PREFIX=13;
    public static final int CARET=14;
    public static final int CHAR=15;
    public static final int COLON=16;
    public static final int COMMA=17;
    public static final int DECIMAL=18;
    public static final int DECIMAL_NEGATIVE=19;
    public static final int DECIMAL_POSITIVE=20;
    public static final int DIGIT=21;
    public static final int DOLLAR=22;
    public static final int DOUBLE=23;
    public static final int DOUBLE_NEGATIVE=24;
    public static final int DOUBLE_POSITIVE=25;
    public static final int DOUBLE_SLASH=26;
    public static final int ECHAR=27;
    public static final int EQUALS=28;
    public static final int EXCLAMATION=29;
    public static final int FALSE=30;
    public static final int GREATER=31;
    public static final int HASH=32;
    public static final int ID=33;
    public static final int ID_CORE=34;
    public static final int ID_START=35;
    public static final int INTEGER=36;
    public static final int INTEGER_NEGATIVE=37;
    public static final int INTEGER_POSITIVE=38;
    public static final int LCR_BRACKET=39;
    public static final int LESS=40;
    public static final int LPAREN=41;
    public static final int LSQ_BRACKET=42;
    public static final int LTSIGN=43;
    public static final int MINUS=44;
    public static final int NAMESPACE=45;
    public static final int NAME_CHAR=46;
    public static final int NAME_START_CHAR=47;
    public static final int NCNAME=48;
    public static final int PERCENT=49;
    public static final int PERIOD=50;
    public static final int PLUS=51;
    public static final int PREFIX=52;
    public static final int PREFIXED_NAME=53;
    public static final int QUESTION=54;
    public static final int QUOTE_DOUBLE=55;
    public static final int QUOTE_SINGLE=56;
    public static final int RCR_BRACKET=57;
    public static final int REFERENCE=58;
    public static final int RPAREN=59;
    public static final int RSQ_BRACKET=60;
    public static final int RTSIGN=61;
    public static final int SCHEMA=62;
    public static final int SEMI=63;
    public static final int SLASH=64;
    public static final int STRING_URI=65;
    public static final int STRING_WITH_QUOTE=66;
    public static final int STRING_WITH_QUOTE_DOUBLE=67;
    public static final int STRING_WITH_TEMPLATE_SIGN=68;
    public static final int TILDE=69;
    public static final int TRUE=70;
    public static final int UNDERSCORE=71;
    public static final int URI_PATH=72;
    public static final int VARNAME=73;
    public static final int WS=74;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public TurtleParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public TurtleParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

    public String[] getTokenNames() { return TurtleParser.tokenNames; }
    public String getGrammarFileName() { return "C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g"; }


    /** Constants */
    private static final String RDF_TYPE = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
    private static final URI RDF_TYPE_URI = URI.create(RDF_TYPE);

    /** Map of directives */
    private HashMap<String, String> directives = new HashMap<String, String>();

    /** The current subject term */
    private NewLiteral subject;

    private Set<NewLiteral> variableSet = new HashSet<NewLiteral>();

    /** A factory to construct the predicates and terms */
    private static final OBDADataFactory dfac = OBDADataFactoryImpl.getInstance();

    private String error = "";

    public String getError() {
    	return error;
    }

    protected void mismatch(IntStream input, int ttype, BitSet follow)
        throws RecognitionException
    {
        throw new MismatchedTokenException(ttype, input);
    }

    public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
        throws RecognitionException
    {
        throw e;
    }

    @Override
    public void recover(IntStream input, RecognitionException re) {
    	throw new RuntimeException(error);
    }

    @Override
    public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
        String hdr = getErrorHeader(e);
        String msg = getErrorMessage(e, tokenNames);
        emitErrorMessage("Syntax error: " + msg + " Location: " + hdr);
    }

    @Override
    public void emitErrorMessage(	String 	msg	 ) 	{
    	error = msg;
    }
        
    public Object recoverFromMismatchedTokenrecoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
    	throws RecognitionException {
        throw new RecognitionException(input);
    }



    // $ANTLR start "parse"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:167:1: parse returns [CQIE value] : ( directiveStatement )* t1= triplesStatement (t2= triplesStatement )* EOF ;
    public final CQIE parse() throws RecognitionException {
        CQIE value = null;


        List<Atom> t1 =null;

        List<Atom> t2 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:168:3: ( ( directiveStatement )* t1= triplesStatement (t2= triplesStatement )* EOF )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:168:5: ( directiveStatement )* t1= triplesStatement (t2= triplesStatement )* EOF
            {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:168:5: ( directiveStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==AT) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:168:5: directiveStatement
            	    {
            	    pushFollow(FOLLOW_directiveStatement_in_parse51);
            	    directiveStatement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            pushFollow(FOLLOW_triplesStatement_in_parse60);
            t1=triplesStatement();

            state._fsp--;



                  int arity = variableSet.size();
                  List<NewLiteral> distinguishVariables = new ArrayList<NewLiteral>(variableSet);
                  Atom head = dfac.getAtom(dfac.getPredicate(OBDALibConstants.QUERY_HEAD_URI, arity, null), distinguishVariables);
                  
                  // Create a new rule
                  List<Atom> triples = t1;
                  value = dfac.getCQIE(head, triples);
                

            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:178:5: (t2= triplesStatement )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==DOLLAR||LA2_0==LESS||(LA2_0 >= PREFIXED_NAME && LA2_0 <= QUESTION)||LA2_0==STRING_WITH_TEMPLATE_SIGN) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:178:6: t2= triplesStatement
            	    {
            	    pushFollow(FOLLOW_triplesStatement_in_parse71);
            	    t2=triplesStatement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);


            match(input,EOF,FOLLOW_EOF_in_parse75); 


                  List<Atom> additionalTriples = t2;
                  if (additionalTriples != null) {
                    // If there are additional triple statements then just add to the existing body
                    List<Atom> existingBody = value.getBody();
                    existingBody.addAll(additionalTriples);
                  }
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "parse"



    // $ANTLR start "directiveStatement"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:188:1: directiveStatement : directive PERIOD ;
    public final void directiveStatement() throws RecognitionException {
        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:189:3: ( directive PERIOD )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:189:5: directive PERIOD
            {
            pushFollow(FOLLOW_directive_in_directiveStatement90);
            directive();

            state._fsp--;


            match(input,PERIOD,FOLLOW_PERIOD_in_directiveStatement92); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "directiveStatement"



    // $ANTLR start "triplesStatement"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:192:1: triplesStatement returns [List<Atom> value] : triples ( WS )* PERIOD ;
    public final List<Atom> triplesStatement() throws RecognitionException {
        List<Atom> value = null;


        List<Atom> triples1 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:193:3: ( triples ( WS )* PERIOD )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:193:5: triples ( WS )* PERIOD
            {
            pushFollow(FOLLOW_triples_in_triplesStatement109);
            triples1=triples();

            state._fsp--;


            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:193:13: ( WS )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==WS) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:193:13: WS
            	    {
            	    match(input,WS,FOLLOW_WS_in_triplesStatement111); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);


            match(input,PERIOD,FOLLOW_PERIOD_in_triplesStatement114); 

             value = triples1; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "triplesStatement"



    // $ANTLR start "directive"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:196:1: directive : ( base | prefixID );
    public final void directive() throws RecognitionException {
        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:197:3: ( base | prefixID )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==AT) ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1==BASE) ) {
                    alt4=1;
                }
                else if ( (LA4_1==PREFIX) ) {
                    alt4=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:197:5: base
                    {
                    pushFollow(FOLLOW_base_in_directive129);
                    base();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:198:5: prefixID
                    {
                    pushFollow(FOLLOW_prefixID_in_directive135);
                    prefixID();

                    state._fsp--;


                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "directive"



    // $ANTLR start "base"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:201:1: base : AT BASE uriref ;
    public final void base() throws RecognitionException {
        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:202:3: ( AT BASE uriref )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:202:5: AT BASE uriref
            {
            match(input,AT,FOLLOW_AT_in_base148); 

            match(input,BASE,FOLLOW_BASE_in_base150); 

            pushFollow(FOLLOW_uriref_in_base152);
            uriref();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "base"



    // $ANTLR start "prefixID"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:205:1: prefixID : AT PREFIX ( namespace | defaultNamespace ) uriref ;
    public final void prefixID() throws RecognitionException {
        TurtleParser.namespace_return namespace2 =null;

        TurtleParser.defaultNamespace_return defaultNamespace3 =null;

        String uriref4 =null;



          String prefix = "";

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:209:3: ( AT PREFIX ( namespace | defaultNamespace ) uriref )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:209:5: AT PREFIX ( namespace | defaultNamespace ) uriref
            {
            match(input,AT,FOLLOW_AT_in_prefixID170); 

            match(input,PREFIX,FOLLOW_PREFIX_in_prefixID172); 

            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:209:15: ( namespace | defaultNamespace )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==NAMESPACE) ) {
                alt5=1;
            }
            else if ( (LA5_0==COLON) ) {
                alt5=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;

            }
            switch (alt5) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:209:16: namespace
                    {
                    pushFollow(FOLLOW_namespace_in_prefixID175);
                    namespace2=namespace();

                    state._fsp--;


                     prefix = (namespace2!=null?input.toString(namespace2.start,namespace2.stop):null); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:209:58: defaultNamespace
                    {
                    pushFollow(FOLLOW_defaultNamespace_in_prefixID181);
                    defaultNamespace3=defaultNamespace();

                    state._fsp--;


                     prefix = (defaultNamespace3!=null?input.toString(defaultNamespace3.start,defaultNamespace3.stop):null); 

                    }
                    break;

            }


            pushFollow(FOLLOW_uriref_in_prefixID186);
            uriref4=uriref();

            state._fsp--;



                  String uriref = uriref4;
                  directives.put(prefix.substring(0, prefix.length()-1), uriref); // remove the end colon
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "prefixID"



    // $ANTLR start "triples"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:215:1: triples returns [List<Atom> value] : subject predicateObjectList ;
    public final List<Atom> triples() throws RecognitionException {
        List<Atom> value = null;


        NewLiteral subject5 =null;

        List<Atom> predicateObjectList6 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:216:3: ( subject predicateObjectList )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:216:5: subject predicateObjectList
            {
            pushFollow(FOLLOW_subject_in_triples205);
            subject5=subject();

            state._fsp--;


             subject = subject5; 

            pushFollow(FOLLOW_predicateObjectList_in_triples209);
            predicateObjectList6=predicateObjectList();

            state._fsp--;



                  value = predicateObjectList6;
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "triples"



    // $ANTLR start "predicateObjectList"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:221:1: predicateObjectList returns [List<Atom> value] : v1= verb l1= objectList ( SEMI v2= verb l2= objectList )* ;
    public final List<Atom> predicateObjectList() throws RecognitionException {
        List<Atom> value = null;


        URI v1 =null;

        List<NewLiteral> l1 =null;

        URI v2 =null;

        List<NewLiteral> l2 =null;



           value = new LinkedList<Atom>();

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:225:3: (v1= verb l1= objectList ( SEMI v2= verb l2= objectList )* )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:225:5: v1= verb l1= objectList ( SEMI v2= verb l2= objectList )*
            {
            pushFollow(FOLLOW_verb_in_predicateObjectList235);
            v1=verb();

            state._fsp--;


            pushFollow(FOLLOW_objectList_in_predicateObjectList239);
            l1=objectList();

            state._fsp--;



                  for (NewLiteral object : l1) {
                    Atom atom = null;
                    if (v1.equals(RDF_TYPE_URI)) {
                      URIConstant c = (URIConstant) object;  // it has to be a URI constant
                      Predicate predicate = dfac.getClassPredicate(c.getURI());
                      atom = dfac.getAtom(predicate, subject);
                    } else {
                      Predicate predicate = dfac.getPredicate(v1, 2, null); // the data type cannot be determined here!
                      atom = dfac.getAtom(predicate, subject, object);
                    }
                    value.add(atom);
                  }
                

            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:239:5: ( SEMI v2= verb l2= objectList )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==SEMI) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:239:6: SEMI v2= verb l2= objectList
            	    {
            	    match(input,SEMI,FOLLOW_SEMI_in_predicateObjectList249); 

            	    pushFollow(FOLLOW_verb_in_predicateObjectList253);
            	    v2=verb();

            	    state._fsp--;


            	    pushFollow(FOLLOW_objectList_in_predicateObjectList257);
            	    l2=objectList();

            	    state._fsp--;



            	          for (NewLiteral object : l2) {
            	            Atom atom = null;
            	            if (v2.equals(RDF_TYPE_URI)) {
            	              URIConstant c = (URIConstant) object;  // it has to be a URI constant
            	              Predicate predicate = dfac.getClassPredicate(c.getURI());
            	              atom = dfac.getAtom(predicate, subject);
            	            } else {
            	              Predicate predicate = dfac.getPredicate(v2, 2, null); // the data type cannot be determined here!
            	              atom = dfac.getAtom(predicate, subject, object);
            	            }
            	            value.add(atom);
            	          }
            	        

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "predicateObjectList"



    // $ANTLR start "verb"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:255:1: verb returns [URI value] : ( predicate | 'a' );
    public final URI verb() throws RecognitionException {
        URI value = null;


        URI predicate7 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:256:3: ( predicate | 'a' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==LESS||LA7_0==PREFIXED_NAME) ) {
                alt7=1;
            }
            else if ( (LA7_0==75) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;

            }
            switch (alt7) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:256:5: predicate
                    {
                    pushFollow(FOLLOW_predicate_in_verb280);
                    predicate7=predicate();

                    state._fsp--;


                     value = predicate7; 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:257:5: 'a'
                    {
                    match(input,75,FOLLOW_75_in_verb288); 

                     value = RDF_TYPE_URI; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "verb"



    // $ANTLR start "objectList"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:260:1: objectList returns [List<NewLiteral> value] : o1= object ( COMMA o2= object )* ;
    public final List<NewLiteral> objectList() throws RecognitionException {
        List<NewLiteral> value = null;


        NewLiteral o1 =null;

        NewLiteral o2 =null;



          value = new ArrayList<NewLiteral>();

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:264:3: (o1= object ( COMMA o2= object )* )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:264:5: o1= object ( COMMA o2= object )*
            {
            pushFollow(FOLLOW_object_in_objectList314);
            o1=object();

            state._fsp--;


             value.add(o1); 

            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:264:42: ( COMMA o2= object )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==COMMA) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:264:43: COMMA o2= object
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_objectList319); 

            	    pushFollow(FOLLOW_object_in_objectList323);
            	    o2=object();

            	    state._fsp--;


            	     value.add(o2); 

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "objectList"



    // $ANTLR start "subject"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:267:1: subject returns [NewLiteral value] : ( resource | variable | function | uriTemplateFunction );
    public final NewLiteral subject() throws RecognitionException {
        NewLiteral value = null;


        URI resource8 =null;

        Variable variable9 =null;

        Function function10 =null;

        Function uriTemplateFunction11 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:268:3: ( resource | variable | function | uriTemplateFunction )
            int alt9=4;
            switch ( input.LA(1) ) {
            case LESS:
                {
                int LA9_1 = input.LA(2);

                if ( (LA9_1==STRING_URI) ) {
                    int LA9_5 = input.LA(3);

                    if ( (LA9_5==GREATER) ) {
                        int LA9_8 = input.LA(4);

                        if ( (LA9_8==LESS||LA9_8==PREFIXED_NAME||LA9_8==75) ) {
                            alt9=1;
                        }
                        else if ( (LA9_8==LPAREN) ) {
                            alt9=3;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 9, 8, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 9, 5, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;

                }
                }
                break;
            case PREFIXED_NAME:
                {
                int LA9_2 = input.LA(2);

                if ( (LA9_2==LESS||LA9_2==PREFIXED_NAME||LA9_2==75) ) {
                    alt9=1;
                }
                else if ( (LA9_2==LPAREN) ) {
                    alt9=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;

                }
                }
                break;
            case DOLLAR:
            case QUESTION:
                {
                alt9=2;
                }
                break;
            case STRING_WITH_TEMPLATE_SIGN:
                {
                alt9=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;

            }

            switch (alt9) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:268:5: resource
                    {
                    pushFollow(FOLLOW_resource_in_subject345);
                    resource8=resource();

                    state._fsp--;


                     value = dfac.getURIConstant(resource8); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:269:5: variable
                    {
                    pushFollow(FOLLOW_variable_in_subject353);
                    variable9=variable();

                    state._fsp--;


                     value = variable9; 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:270:5: function
                    {
                    pushFollow(FOLLOW_function_in_subject361);
                    function10=function();

                    state._fsp--;


                     value = function10; 

                    }
                    break;
                case 4 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:271:5: uriTemplateFunction
                    {
                    pushFollow(FOLLOW_uriTemplateFunction_in_subject369);
                    uriTemplateFunction11=uriTemplateFunction();

                    state._fsp--;


                     value = uriTemplateFunction11; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "subject"



    // $ANTLR start "predicate"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:275:1: predicate returns [URI value] : resource ;
    public final URI predicate() throws RecognitionException {
        URI value = null;


        URI resource12 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:276:3: ( resource )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:276:5: resource
            {
            pushFollow(FOLLOW_resource_in_predicate389);
            resource12=resource();

            state._fsp--;


             value = resource12; 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "predicate"



    // $ANTLR start "object"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:279:1: object returns [NewLiteral value] : ( resource | function | literal | variable | dataTypeFunction | uriTemplateFunction );
    public final NewLiteral object() throws RecognitionException {
        NewLiteral value = null;


        URI resource13 =null;

        Function function14 =null;

        NewLiteral literal15 =null;

        Variable variable16 =null;

        Function dataTypeFunction17 =null;

        Function uriTemplateFunction18 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:280:3: ( resource | function | literal | variable | dataTypeFunction | uriTemplateFunction )
            int alt10=6;
            switch ( input.LA(1) ) {
            case LESS:
                {
                int LA10_1 = input.LA(2);

                if ( (LA10_1==STRING_URI) ) {
                    int LA10_6 = input.LA(3);

                    if ( (LA10_6==GREATER) ) {
                        int LA10_10 = input.LA(4);

                        if ( (LA10_10==COMMA||LA10_10==PERIOD||LA10_10==SEMI||LA10_10==WS) ) {
                            alt10=1;
                        }
                        else if ( (LA10_10==LPAREN) ) {
                            alt10=2;
                        }
                        else {
                            NoViableAltException nvae =
                                new NoViableAltException("", 10, 10, input);

                            throw nvae;

                        }
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 6, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 1, input);

                    throw nvae;

                }
                }
                break;
            case PREFIXED_NAME:
                {
                int LA10_2 = input.LA(2);

                if ( (LA10_2==COMMA||LA10_2==PERIOD||LA10_2==SEMI||LA10_2==WS) ) {
                    alt10=1;
                }
                else if ( (LA10_2==LPAREN) ) {
                    alt10=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 2, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
            case DECIMAL_NEGATIVE:
            case DECIMAL_POSITIVE:
            case DOUBLE:
            case DOUBLE_NEGATIVE:
            case DOUBLE_POSITIVE:
            case FALSE:
            case INTEGER:
            case INTEGER_NEGATIVE:
            case INTEGER_POSITIVE:
            case STRING_WITH_QUOTE_DOUBLE:
            case TRUE:
                {
                alt10=3;
                }
                break;
            case DOLLAR:
            case QUESTION:
                {
                int LA10_4 = input.LA(2);

                if ( (LA10_4==VARNAME) ) {
                    int LA10_9 = input.LA(3);

                    if ( (LA10_9==COMMA||LA10_9==PERIOD||LA10_9==SEMI||LA10_9==WS) ) {
                        alt10=4;
                    }
                    else if ( (LA10_9==AT||LA10_9==REFERENCE) ) {
                        alt10=5;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 10, 9, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 10, 4, input);

                    throw nvae;

                }
                }
                break;
            case STRING_WITH_TEMPLATE_SIGN:
                {
                alt10=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;

            }

            switch (alt10) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:280:5: resource
                    {
                    pushFollow(FOLLOW_resource_in_object408);
                    resource13=resource();

                    state._fsp--;


                     value = dfac.getURIConstant(resource13); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:281:5: function
                    {
                    pushFollow(FOLLOW_function_in_object416);
                    function14=function();

                    state._fsp--;


                     value = function14; 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:282:5: literal
                    {
                    pushFollow(FOLLOW_literal_in_object424);
                    literal15=literal();

                    state._fsp--;


                     value = literal15; 

                    }
                    break;
                case 4 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:283:5: variable
                    {
                    pushFollow(FOLLOW_variable_in_object433);
                    variable16=variable();

                    state._fsp--;


                     value = variable16; 

                    }
                    break;
                case 5 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:284:5: dataTypeFunction
                    {
                    pushFollow(FOLLOW_dataTypeFunction_in_object441);
                    dataTypeFunction17=dataTypeFunction();

                    state._fsp--;


                     value = dataTypeFunction17; 

                    }
                    break;
                case 6 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:285:5: uriTemplateFunction
                    {
                    pushFollow(FOLLOW_uriTemplateFunction_in_object449);
                    uriTemplateFunction18=uriTemplateFunction();

                    state._fsp--;


                     value = uriTemplateFunction18; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "object"



    // $ANTLR start "resource"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:289:1: resource returns [URI value] : ( uriref | qname );
    public final URI resource() throws RecognitionException {
        URI value = null;


        String uriref19 =null;

        String qname20 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:290:3: ( uriref | qname )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==LESS) ) {
                alt11=1;
            }
            else if ( (LA11_0==PREFIXED_NAME) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;

            }
            switch (alt11) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:290:5: uriref
                    {
                    pushFollow(FOLLOW_uriref_in_resource469);
                    uriref19=uriref();

                    state._fsp--;


                     value = URI.create(uriref19); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:291:5: qname
                    {
                    pushFollow(FOLLOW_qname_in_resource477);
                    qname20=qname();

                    state._fsp--;


                     value = URI.create(qname20); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "resource"



    // $ANTLR start "uriref"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:294:1: uriref returns [String value] : LESS relativeURI GREATER ;
    public final String uriref() throws RecognitionException {
        String value = null;


        TurtleParser.relativeURI_return relativeURI21 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:295:3: ( LESS relativeURI GREATER )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:295:5: LESS relativeURI GREATER
            {
            match(input,LESS,FOLLOW_LESS_in_uriref496); 

            pushFollow(FOLLOW_relativeURI_in_uriref498);
            relativeURI21=relativeURI();

            state._fsp--;


            match(input,GREATER,FOLLOW_GREATER_in_uriref500); 

             value = (relativeURI21!=null?input.toString(relativeURI21.start,relativeURI21.stop):null); 

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "uriref"



    // $ANTLR start "qname"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:298:1: qname returns [String value] : PREFIXED_NAME ;
    public final String qname() throws RecognitionException {
        String value = null;


        Token PREFIXED_NAME22=null;

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:299:3: ( PREFIXED_NAME )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:299:5: PREFIXED_NAME
            {
            PREFIXED_NAME22=(Token)match(input,PREFIXED_NAME,FOLLOW_PREFIXED_NAME_in_qname519); 


                  String[] tokens = (PREFIXED_NAME22!=null?PREFIXED_NAME22.getText():null).split(":", 2);
                  String uri = directives.get(tokens[0]);  // the first token is the prefix
                  value = uri + tokens[1];  // the second token is the local name
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "qname"



    // $ANTLR start "blank"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:306:1: blank : ( nodeID | BLANK );
    public final void blank() throws RecognitionException {
        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:307:3: ( nodeID | BLANK )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==BLANK_PREFIX) ) {
                alt12=1;
            }
            else if ( (LA12_0==BLANK) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;

            }
            switch (alt12) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:307:5: nodeID
                    {
                    pushFollow(FOLLOW_nodeID_in_blank534);
                    nodeID();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:308:5: BLANK
                    {
                    match(input,BLANK,FOLLOW_BLANK_in_blank540); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "blank"



    // $ANTLR start "variable"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:311:1: variable returns [Variable value] : ( QUESTION | DOLLAR ) name ;
    public final Variable variable() throws RecognitionException {
        Variable value = null;


        TurtleParser.name_return name23 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:312:3: ( ( QUESTION | DOLLAR ) name )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:312:5: ( QUESTION | DOLLAR ) name
            {
            if ( input.LA(1)==DOLLAR||input.LA(1)==QUESTION ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            pushFollow(FOLLOW_name_in_variable563);
            name23=name();

            state._fsp--;



                   value = dfac.getVariable((name23!=null?input.toString(name23.start,name23.stop):null));
                   variableSet.add(value);
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "variable"



    // $ANTLR start "function"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:318:1: function returns [Function value] : resource LPAREN terms RPAREN ;
    public final Function function() throws RecognitionException {
        Function value = null;


        URI resource24 =null;

        Vector<NewLiteral> terms25 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:319:3: ( resource LPAREN terms RPAREN )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:319:5: resource LPAREN terms RPAREN
            {
            pushFollow(FOLLOW_resource_in_function582);
            resource24=resource();

            state._fsp--;


            match(input,LPAREN,FOLLOW_LPAREN_in_function584); 

            pushFollow(FOLLOW_terms_in_function586);
            terms25=terms();

            state._fsp--;


            match(input,RPAREN,FOLLOW_RPAREN_in_function588); 


                  String functionName = resource24.toString();
                  int arity = terms25.size();
                  Predicate functionSymbol = dfac.getPredicate(URI.create(functionName), arity);
                  value = dfac.getFunctionalTerm(functionSymbol, terms25);
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "function"



    // $ANTLR start "dataTypeFunction"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:327:1: dataTypeFunction returns [Function value] : ( variable AT language | variable REFERENCE resource );
    public final Function dataTypeFunction() throws RecognitionException {
        Function value = null;


        Variable variable26 =null;

        NewLiteral language27 =null;

        Variable variable28 =null;

        URI resource29 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:328:3: ( variable AT language | variable REFERENCE resource )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==DOLLAR||LA13_0==QUESTION) ) {
                int LA13_1 = input.LA(2);

                if ( (LA13_1==VARNAME) ) {
                    int LA13_2 = input.LA(3);

                    if ( (LA13_2==AT) ) {
                        alt13=1;
                    }
                    else if ( (LA13_2==REFERENCE) ) {
                        alt13=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 13, 2, input);

                        throw nvae;

                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:328:5: variable AT language
                    {
                    pushFollow(FOLLOW_variable_in_dataTypeFunction607);
                    variable26=variable();

                    state._fsp--;


                    match(input,AT,FOLLOW_AT_in_dataTypeFunction609); 

                    pushFollow(FOLLOW_language_in_dataTypeFunction611);
                    language27=language();

                    state._fsp--;



                          Predicate functionSymbol = dfac.getDataTypePredicateLiteralLang();
                          Variable var = variable26;
                          NewLiteral lang = language27;   
                          value = dfac.getFunctionalTerm(functionSymbol, var, lang);
                        

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:334:5: variable REFERENCE resource
                    {
                    pushFollow(FOLLOW_variable_in_dataTypeFunction619);
                    variable28=variable();

                    state._fsp--;


                    match(input,REFERENCE,FOLLOW_REFERENCE_in_dataTypeFunction621); 

                    pushFollow(FOLLOW_resource_in_dataTypeFunction623);
                    resource29=resource();

                    state._fsp--;



                          Variable var = variable28;
                          String functionName = resource29.toString();
                          Predicate functionSymbol = null;
                          if (functionName.equals(OBDAVocabulary.RDFS_LITERAL_URI)) {
                        	functionSymbol = dfac.getDataTypePredicateLiteral();
                          } else if (functionName.equals(OBDAVocabulary.XSD_STRING_URI)) {
                        	functionSymbol = dfac.getDataTypePredicateString();
                          } else if (functionName.equals(OBDAVocabulary.XSD_INTEGER_URI) || functionName.equals(OBDAVocabulary.XSD_INT_URI)) {
                         	functionSymbol = dfac.getDataTypePredicateInteger();
                          } else if (functionName.equals(OBDAVocabulary.XSD_DECIMAL_URI)) {
                        	functionSymbol = dfac.getDataTypePredicateDecimal();
                          } else if (functionName.equals(OBDAVocabulary.XSD_DOUBLE_URI)) {
                        	functionSymbol = dfac.getDataTypePredicateDouble();
                          } else if (functionName.equals(OBDAVocabulary.XSD_DATETIME_URI)) {
                        	functionSymbol = dfac.getDataTypePredicateDateTime();
                          } else if (functionName.equals(OBDAVocabulary.XSD_BOOLEAN_URI)) {
                        	functionSymbol = dfac.getDataTypePredicateBoolean();
                          } else {
                            throw new RecognitionException();
                          }
                          value = dfac.getFunctionalTerm(functionSymbol, var);
                        

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "dataTypeFunction"



    // $ANTLR start "language"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:359:1: language returns [NewLiteral value] : ( languageTag | variable );
    public final NewLiteral language() throws RecognitionException {
        NewLiteral value = null;


        TurtleParser.languageTag_return languageTag30 =null;

        Variable variable31 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:360:3: ( languageTag | variable )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==VARNAME) ) {
                alt14=1;
            }
            else if ( (LA14_0==DOLLAR||LA14_0==QUESTION) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;

            }
            switch (alt14) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:360:5: languageTag
                    {
                    pushFollow(FOLLOW_languageTag_in_language642);
                    languageTag30=languageTag();

                    state._fsp--;



                        	value = dfac.getValueConstant((languageTag30!=null?input.toString(languageTag30.start,languageTag30.stop):null).toLowerCase(), COL_TYPE.STRING);
                        

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:363:5: variable
                    {
                    pushFollow(FOLLOW_variable_in_language650);
                    variable31=variable();

                    state._fsp--;



                        	value = variable31;
                        

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "language"



    // $ANTLR start "uriTemplateFunction"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:368:1: uriTemplateFunction returns [Function value] : STRING_WITH_TEMPLATE_SIGN ;
    public final Function uriTemplateFunction() throws RecognitionException {
        Function value = null;


        Token STRING_WITH_TEMPLATE_SIGN32=null;


          List<NewLiteral> terms = new ArrayList<NewLiteral>();

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:372:3: ( STRING_WITH_TEMPLATE_SIGN )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:372:5: STRING_WITH_TEMPLATE_SIGN
            {
            STRING_WITH_TEMPLATE_SIGN32=(Token)match(input,STRING_WITH_TEMPLATE_SIGN,FOLLOW_STRING_WITH_TEMPLATE_SIGN_in_uriTemplateFunction674); 


                  String template = (STRING_WITH_TEMPLATE_SIGN32!=null?STRING_WITH_TEMPLATE_SIGN32.getText():null);
                  
                  // cleanup the template string, e.g., <"&ex;student-{pid}"> --> &ex;student-{pid}
                  template = template.substring(2, template.length()-2);
                  
                  if (template.contains("&") && template.contains(";")) {
                    // scan the input string if it contains "&...;"
                    int start = template.indexOf("&");
                    int end = template.indexOf(";");
                    
                    // extract the whole prefix placeholder, e.g., "&ex;"
                    String prefixPlaceHolder = template.substring(start, end+1);
                    
                    // extract the prefix name, e.g., "&ex;" --> "ex"
                    String prefix = prefixPlaceHolder.substring(1, prefixPlaceHolder.length()-1);
                    
                    // replace any colon sign
                    prefix = prefix.replace(":", "");
                    
                    String uri = directives.get(prefix);
                    if (uri == null) {
                      throw new RuntimeException("The prefix name is unknown: " + prefix); // the prefix is unknown.
                    }
                    template = template.replaceFirst(prefixPlaceHolder, uri);
                  }
                  
                  while (template.contains("{") && template.contains("}")) {
                    // scan the input string if it contains "{" ... "}"
                    int start = template.indexOf("{");
                    int end = template.indexOf("}");
                    
                    // extract the whole placeholder, e.g., "{?var}"
                    String placeHolder = Pattern.quote(template.substring(start, end+1));
                    template = template.replaceFirst(placeHolder, "[]"); // change the placeholder string temporarly
                    
                    // extract the variable name only, e.g., "{?var}" --> "var"
                    try {
                   	  String variableName = placeHolder.substring(4, placeHolder.length()-3);
                   	  if (variableName.equals("")) {
                   	    throw new RuntimeException("Variable name must have at least 1 character");
                   	  }
                      terms.add(dfac.getVariable(variableName));
                    } catch (IndexOutOfBoundsException e) {
                   	  throw new RuntimeException("Variable name must have at least 1 character");
                    }
                  }
                  // replace the placeholder string to the original. The current string becomes the template
                  template = template.replace("[]", "{}");
                  ValueConstant uriTemplate = dfac.getValueConstant(template);
                  
                  // the URI template is always on the first position in the term list
                  terms.add(0, uriTemplate);
                  value = dfac.getFunctionalTerm(dfac.getUriTemplatePredicate(terms.size()), terms);
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "uriTemplateFunction"



    // $ANTLR start "terms"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:429:1: terms returns [Vector<NewLiteral> value] : t1= term ( COMMA t2= term )* ;
    public final Vector<NewLiteral> terms() throws RecognitionException {
        Vector<NewLiteral> value = null;


        NewLiteral t1 =null;

        NewLiteral t2 =null;



          value = new Vector<NewLiteral>();

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:433:3: (t1= term ( COMMA t2= term )* )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:433:5: t1= term ( COMMA t2= term )*
            {
            pushFollow(FOLLOW_term_in_terms700);
            t1=term();

            state._fsp--;


             value.add(t1); 

            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:433:40: ( COMMA t2= term )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==COMMA) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:433:41: COMMA t2= term
            	    {
            	    match(input,COMMA,FOLLOW_COMMA_in_terms705); 

            	    pushFollow(FOLLOW_term_in_terms709);
            	    t2=term();

            	    state._fsp--;


            	     value.add(t2); 

            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "terms"



    // $ANTLR start "term"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:436:1: term returns [NewLiteral value] : ( function | variable | literal );
    public final NewLiteral term() throws RecognitionException {
        NewLiteral value = null;


        Function function33 =null;

        Variable variable34 =null;

        NewLiteral literal35 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:437:3: ( function | variable | literal )
            int alt16=3;
            switch ( input.LA(1) ) {
            case LESS:
            case PREFIXED_NAME:
                {
                alt16=1;
                }
                break;
            case DOLLAR:
            case QUESTION:
                {
                alt16=2;
                }
                break;
            case DECIMAL:
            case DECIMAL_NEGATIVE:
            case DECIMAL_POSITIVE:
            case DOUBLE:
            case DOUBLE_NEGATIVE:
            case DOUBLE_POSITIVE:
            case FALSE:
            case INTEGER:
            case INTEGER_NEGATIVE:
            case INTEGER_POSITIVE:
            case STRING_WITH_QUOTE_DOUBLE:
            case TRUE:
                {
                alt16=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;

            }

            switch (alt16) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:437:5: function
                    {
                    pushFollow(FOLLOW_function_in_term730);
                    function33=function();

                    state._fsp--;


                     value = function33; 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:438:5: variable
                    {
                    pushFollow(FOLLOW_variable_in_term738);
                    variable34=variable();

                    state._fsp--;


                     value = variable34; 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:439:5: literal
                    {
                    pushFollow(FOLLOW_literal_in_term746);
                    literal35=literal();

                    state._fsp--;


                     value = literal35; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "term"



    // $ANTLR start "literal"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:442:1: literal returns [NewLiteral value] : ( stringLiteral ( AT language )? | dataTypeString | numericLiteral | booleanLiteral );
    public final NewLiteral literal() throws RecognitionException {
        NewLiteral value = null;


        ValueConstant stringLiteral36 =null;

        NewLiteral language37 =null;

        NewLiteral dataTypeString38 =null;

        ValueConstant numericLiteral39 =null;

        ValueConstant booleanLiteral40 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:443:3: ( stringLiteral ( AT language )? | dataTypeString | numericLiteral | booleanLiteral )
            int alt18=4;
            switch ( input.LA(1) ) {
            case STRING_WITH_QUOTE_DOUBLE:
                {
                int LA18_1 = input.LA(2);

                if ( (LA18_1==AT||LA18_1==COMMA||LA18_1==PERIOD||LA18_1==RPAREN||LA18_1==SEMI||LA18_1==WS) ) {
                    alt18=1;
                }
                else if ( (LA18_1==REFERENCE) ) {
                    alt18=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;

                }
                }
                break;
            case DECIMAL:
            case DECIMAL_NEGATIVE:
            case DECIMAL_POSITIVE:
            case DOUBLE:
            case DOUBLE_NEGATIVE:
            case DOUBLE_POSITIVE:
            case INTEGER:
            case INTEGER_NEGATIVE:
            case INTEGER_POSITIVE:
                {
                alt18=3;
                }
                break;
            case FALSE:
            case TRUE:
                {
                alt18=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }

            switch (alt18) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:443:5: stringLiteral ( AT language )?
                    {
                    pushFollow(FOLLOW_stringLiteral_in_literal765);
                    stringLiteral36=stringLiteral();

                    state._fsp--;


                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:443:19: ( AT language )?
                    int alt17=2;
                    int LA17_0 = input.LA(1);

                    if ( (LA17_0==AT) ) {
                        alt17=1;
                    }
                    switch (alt17) {
                        case 1 :
                            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:443:20: AT language
                            {
                            match(input,AT,FOLLOW_AT_in_literal768); 

                            pushFollow(FOLLOW_language_in_literal770);
                            language37=language();

                            state._fsp--;


                            }
                            break;

                    }



                           ValueConstant constant = stringLiteral36;
                           NewLiteral lang = language37;
                           if (lang != null) {
                             value = dfac.getFunctionalTerm(dfac.getDataTypePredicateLiteralLang(), constant, lang);
                           } else {
                           	 value = dfac.getFunctionalTerm(dfac.getDataTypePredicateLiteral(), constant);
                           }
                        

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:452:5: dataTypeString
                    {
                    pushFollow(FOLLOW_dataTypeString_in_literal780);
                    dataTypeString38=dataTypeString();

                    state._fsp--;


                     value = dataTypeString38; 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:453:5: numericLiteral
                    {
                    pushFollow(FOLLOW_numericLiteral_in_literal788);
                    numericLiteral39=numericLiteral();

                    state._fsp--;


                     value = numericLiteral39; 

                    }
                    break;
                case 4 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:454:5: booleanLiteral
                    {
                    pushFollow(FOLLOW_booleanLiteral_in_literal796);
                    booleanLiteral40=booleanLiteral();

                    state._fsp--;


                     value = booleanLiteral40; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "literal"



    // $ANTLR start "stringLiteral"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:457:1: stringLiteral returns [ValueConstant value] : STRING_WITH_QUOTE_DOUBLE ;
    public final ValueConstant stringLiteral() throws RecognitionException {
        ValueConstant value = null;


        Token STRING_WITH_QUOTE_DOUBLE41=null;

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:458:3: ( STRING_WITH_QUOTE_DOUBLE )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:458:5: STRING_WITH_QUOTE_DOUBLE
            {
            STRING_WITH_QUOTE_DOUBLE41=(Token)match(input,STRING_WITH_QUOTE_DOUBLE,FOLLOW_STRING_WITH_QUOTE_DOUBLE_in_stringLiteral815); 


                  String str = (STRING_WITH_QUOTE_DOUBLE41!=null?STRING_WITH_QUOTE_DOUBLE41.getText():null);
                  value = dfac.getValueConstant(str.substring(1, str.length()-1), COL_TYPE.LITERAL); // without the double quotes
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "stringLiteral"



    // $ANTLR start "dataTypeString"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:464:1: dataTypeString returns [NewLiteral value] : stringLiteral REFERENCE resource ;
    public final NewLiteral dataTypeString() throws RecognitionException {
        NewLiteral value = null;


        ValueConstant stringLiteral42 =null;

        URI resource43 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:465:3: ( stringLiteral REFERENCE resource )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:465:6: stringLiteral REFERENCE resource
            {
            pushFollow(FOLLOW_stringLiteral_in_dataTypeString835);
            stringLiteral42=stringLiteral();

            state._fsp--;


            match(input,REFERENCE,FOLLOW_REFERENCE_in_dataTypeString837); 

            pushFollow(FOLLOW_resource_in_dataTypeString839);
            resource43=resource();

            state._fsp--;



                  ValueConstant constant = stringLiteral42;
                  String functionName = resource43.toString();
                  Predicate functionSymbol = null;
                  if (functionName.equals(OBDAVocabulary.RDFS_LITERAL_URI)) {
                	functionSymbol = dfac.getDataTypePredicateLiteral();
                  } else if (functionName.equals(OBDAVocabulary.XSD_STRING_URI)) {
                	functionSymbol = dfac.getDataTypePredicateString();
                  } else if (functionName.equals(OBDAVocabulary.XSD_INTEGER_URI)) {
                 	functionSymbol = dfac.getDataTypePredicateInteger();
                  } else if (functionName.equals(OBDAVocabulary.XSD_DECIMAL_URI)) {
                	functionSymbol = dfac.getDataTypePredicateDecimal();
                  } else if (functionName.equals(OBDAVocabulary.XSD_DOUBLE_URI)) {
                	functionSymbol = dfac.getDataTypePredicateDouble();
                  } else if (functionName.equals(OBDAVocabulary.XSD_DATETIME_URI)) {
                	functionSymbol = dfac.getDataTypePredicateDateTime();
                  } else if (functionName.equals(OBDAVocabulary.XSD_BOOLEAN_URI)) {
                	functionSymbol = dfac.getDataTypePredicateBoolean();
                  } else {
                    throw new RuntimeException("Unknown datatype: " + functionName);
                  }
                  value = dfac.getFunctionalTerm(functionSymbol, constant);
                

            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "dataTypeString"



    // $ANTLR start "numericLiteral"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:490:1: numericLiteral returns [ValueConstant value] : ( numericUnsigned | numericPositive | numericNegative );
    public final ValueConstant numericLiteral() throws RecognitionException {
        ValueConstant value = null;


        ValueConstant numericUnsigned44 =null;

        ValueConstant numericPositive45 =null;

        ValueConstant numericNegative46 =null;


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:491:3: ( numericUnsigned | numericPositive | numericNegative )
            int alt19=3;
            switch ( input.LA(1) ) {
            case DECIMAL:
            case DOUBLE:
            case INTEGER:
                {
                alt19=1;
                }
                break;
            case DECIMAL_POSITIVE:
            case DOUBLE_POSITIVE:
            case INTEGER_POSITIVE:
                {
                alt19=2;
                }
                break;
            case DECIMAL_NEGATIVE:
            case DOUBLE_NEGATIVE:
            case INTEGER_NEGATIVE:
                {
                alt19=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;

            }

            switch (alt19) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:491:5: numericUnsigned
                    {
                    pushFollow(FOLLOW_numericUnsigned_in_numericLiteral858);
                    numericUnsigned44=numericUnsigned();

                    state._fsp--;


                     value = numericUnsigned44; 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:492:5: numericPositive
                    {
                    pushFollow(FOLLOW_numericPositive_in_numericLiteral866);
                    numericPositive45=numericPositive();

                    state._fsp--;


                     value = numericPositive45; 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:493:5: numericNegative
                    {
                    pushFollow(FOLLOW_numericNegative_in_numericLiteral874);
                    numericNegative46=numericNegative();

                    state._fsp--;


                     value = numericNegative46; 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "numericLiteral"



    // $ANTLR start "nodeID"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:496:1: nodeID : BLANK_PREFIX name ;
    public final void nodeID() throws RecognitionException {
        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:497:3: ( BLANK_PREFIX name )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:497:5: BLANK_PREFIX name
            {
            match(input,BLANK_PREFIX,FOLLOW_BLANK_PREFIX_in_nodeID889); 

            pushFollow(FOLLOW_name_in_nodeID891);
            name();

            state._fsp--;


            }

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return ;
    }
    // $ANTLR end "nodeID"


    public static class relativeURI_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "relativeURI"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:500:1: relativeURI : STRING_URI ;
    public final TurtleParser.relativeURI_return relativeURI() throws RecognitionException {
        TurtleParser.relativeURI_return retval = new TurtleParser.relativeURI_return();
        retval.start = input.LT(1);


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:501:3: ( STRING_URI )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:501:5: STRING_URI
            {
            match(input,STRING_URI,FOLLOW_STRING_URI_in_relativeURI904); 

            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "relativeURI"


    public static class namespace_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "namespace"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:504:1: namespace : NAMESPACE ;
    public final TurtleParser.namespace_return namespace() throws RecognitionException {
        TurtleParser.namespace_return retval = new TurtleParser.namespace_return();
        retval.start = input.LT(1);


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:505:3: ( NAMESPACE )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:505:5: NAMESPACE
            {
            match(input,NAMESPACE,FOLLOW_NAMESPACE_in_namespace917); 

            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "namespace"


    public static class defaultNamespace_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "defaultNamespace"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:508:1: defaultNamespace : COLON ;
    public final TurtleParser.defaultNamespace_return defaultNamespace() throws RecognitionException {
        TurtleParser.defaultNamespace_return retval = new TurtleParser.defaultNamespace_return();
        retval.start = input.LT(1);


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:509:3: ( COLON )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:509:5: COLON
            {
            match(input,COLON,FOLLOW_COLON_in_defaultNamespace932); 

            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "defaultNamespace"


    public static class name_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "name"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:512:1: name : VARNAME ;
    public final TurtleParser.name_return name() throws RecognitionException {
        TurtleParser.name_return retval = new TurtleParser.name_return();
        retval.start = input.LT(1);


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:513:3: ( VARNAME )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:513:5: VARNAME
            {
            match(input,VARNAME,FOLLOW_VARNAME_in_name945); 

            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "name"


    public static class languageTag_return extends ParserRuleReturnScope {
    };


    // $ANTLR start "languageTag"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:516:1: languageTag : VARNAME ;
    public final TurtleParser.languageTag_return languageTag() throws RecognitionException {
        TurtleParser.languageTag_return retval = new TurtleParser.languageTag_return();
        retval.start = input.LT(1);


        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:517:3: ( VARNAME )
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:517:5: VARNAME
            {
            match(input,VARNAME,FOLLOW_VARNAME_in_languageTag958); 

            }

            retval.stop = input.LT(-1);


        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "languageTag"



    // $ANTLR start "booleanLiteral"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:520:1: booleanLiteral returns [ValueConstant value] : ( TRUE | FALSE );
    public final ValueConstant booleanLiteral() throws RecognitionException {
        ValueConstant value = null;


        Token TRUE47=null;
        Token FALSE48=null;

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:521:3: ( TRUE | FALSE )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==TRUE) ) {
                alt20=1;
            }
            else if ( (LA20_0==FALSE) ) {
                alt20=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;

            }
            switch (alt20) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:521:5: TRUE
                    {
                    TRUE47=(Token)match(input,TRUE,FOLLOW_TRUE_in_booleanLiteral975); 

                     value = dfac.getValueConstant((TRUE47!=null?TRUE47.getText():null), COL_TYPE.BOOLEAN); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:522:5: FALSE
                    {
                    FALSE48=(Token)match(input,FALSE,FOLLOW_FALSE_in_booleanLiteral984); 

                     value = dfac.getValueConstant((FALSE48!=null?FALSE48.getText():null), COL_TYPE.BOOLEAN); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "booleanLiteral"



    // $ANTLR start "numericUnsigned"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:525:1: numericUnsigned returns [ValueConstant value] : ( INTEGER | DOUBLE | DECIMAL );
    public final ValueConstant numericUnsigned() throws RecognitionException {
        ValueConstant value = null;


        Token INTEGER49=null;
        Token DOUBLE50=null;
        Token DECIMAL51=null;

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:526:3: ( INTEGER | DOUBLE | DECIMAL )
            int alt21=3;
            switch ( input.LA(1) ) {
            case INTEGER:
                {
                alt21=1;
                }
                break;
            case DOUBLE:
                {
                alt21=2;
                }
                break;
            case DECIMAL:
                {
                alt21=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;

            }

            switch (alt21) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:526:5: INTEGER
                    {
                    INTEGER49=(Token)match(input,INTEGER,FOLLOW_INTEGER_in_numericUnsigned1003); 

                     value = dfac.getValueConstant((INTEGER49!=null?INTEGER49.getText():null), COL_TYPE.INTEGER); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:527:5: DOUBLE
                    {
                    DOUBLE50=(Token)match(input,DOUBLE,FOLLOW_DOUBLE_in_numericUnsigned1011); 

                     value = dfac.getValueConstant((DOUBLE50!=null?DOUBLE50.getText():null), COL_TYPE.DOUBLE); 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:528:5: DECIMAL
                    {
                    DECIMAL51=(Token)match(input,DECIMAL,FOLLOW_DECIMAL_in_numericUnsigned1020); 

                     value = dfac.getValueConstant((DECIMAL51!=null?DECIMAL51.getText():null), COL_TYPE.DECIMAL); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "numericUnsigned"



    // $ANTLR start "numericPositive"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:531:1: numericPositive returns [ValueConstant value] : ( INTEGER_POSITIVE | DOUBLE_POSITIVE | DECIMAL_POSITIVE );
    public final ValueConstant numericPositive() throws RecognitionException {
        ValueConstant value = null;


        Token INTEGER_POSITIVE52=null;
        Token DOUBLE_POSITIVE53=null;
        Token DECIMAL_POSITIVE54=null;

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:532:3: ( INTEGER_POSITIVE | DOUBLE_POSITIVE | DECIMAL_POSITIVE )
            int alt22=3;
            switch ( input.LA(1) ) {
            case INTEGER_POSITIVE:
                {
                alt22=1;
                }
                break;
            case DOUBLE_POSITIVE:
                {
                alt22=2;
                }
                break;
            case DECIMAL_POSITIVE:
                {
                alt22=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;

            }

            switch (alt22) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:532:5: INTEGER_POSITIVE
                    {
                    INTEGER_POSITIVE52=(Token)match(input,INTEGER_POSITIVE,FOLLOW_INTEGER_POSITIVE_in_numericPositive1039); 

                     value = dfac.getValueConstant((INTEGER_POSITIVE52!=null?INTEGER_POSITIVE52.getText():null), COL_TYPE.INTEGER); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:533:5: DOUBLE_POSITIVE
                    {
                    DOUBLE_POSITIVE53=(Token)match(input,DOUBLE_POSITIVE,FOLLOW_DOUBLE_POSITIVE_in_numericPositive1047); 

                     value = dfac.getValueConstant((DOUBLE_POSITIVE53!=null?DOUBLE_POSITIVE53.getText():null), COL_TYPE.DOUBLE); 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:534:5: DECIMAL_POSITIVE
                    {
                    DECIMAL_POSITIVE54=(Token)match(input,DECIMAL_POSITIVE,FOLLOW_DECIMAL_POSITIVE_in_numericPositive1056); 

                     value = dfac.getValueConstant((DECIMAL_POSITIVE54!=null?DECIMAL_POSITIVE54.getText():null), COL_TYPE.DECIMAL); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "numericPositive"



    // $ANTLR start "numericNegative"
    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:537:1: numericNegative returns [ValueConstant value] : ( INTEGER_NEGATIVE | DOUBLE_NEGATIVE | DECIMAL_NEGATIVE );
    public final ValueConstant numericNegative() throws RecognitionException {
        ValueConstant value = null;


        Token INTEGER_NEGATIVE55=null;
        Token DOUBLE_NEGATIVE56=null;
        Token DECIMAL_NEGATIVE57=null;

        try {
            // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:538:3: ( INTEGER_NEGATIVE | DOUBLE_NEGATIVE | DECIMAL_NEGATIVE )
            int alt23=3;
            switch ( input.LA(1) ) {
            case INTEGER_NEGATIVE:
                {
                alt23=1;
                }
                break;
            case DOUBLE_NEGATIVE:
                {
                alt23=2;
                }
                break;
            case DECIMAL_NEGATIVE:
                {
                alt23=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;

            }

            switch (alt23) {
                case 1 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:538:5: INTEGER_NEGATIVE
                    {
                    INTEGER_NEGATIVE55=(Token)match(input,INTEGER_NEGATIVE,FOLLOW_INTEGER_NEGATIVE_in_numericNegative1075); 

                     value = dfac.getValueConstant((INTEGER_NEGATIVE55!=null?INTEGER_NEGATIVE55.getText():null), COL_TYPE.INTEGER); 

                    }
                    break;
                case 2 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:539:5: DOUBLE_NEGATIVE
                    {
                    DOUBLE_NEGATIVE56=(Token)match(input,DOUBLE_NEGATIVE,FOLLOW_DOUBLE_NEGATIVE_in_numericNegative1083); 

                     value = dfac.getValueConstant((DOUBLE_NEGATIVE56!=null?DOUBLE_NEGATIVE56.getText():null), COL_TYPE.DOUBLE); 

                    }
                    break;
                case 3 :
                    // C:\\Project\\Code\\obdalib-parent\\obdalib-core\\src\\main\\java\\it\\unibz\\krdb\\obda\\parser\\Turtle.g:540:5: DECIMAL_NEGATIVE
                    {
                    DECIMAL_NEGATIVE57=(Token)match(input,DECIMAL_NEGATIVE,FOLLOW_DECIMAL_NEGATIVE_in_numericNegative1092); 

                     value = dfac.getValueConstant((DECIMAL_NEGATIVE57!=null?DECIMAL_NEGATIVE57.getText():null), COL_TYPE.DECIMAL); 

                    }
                    break;

            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
        }

        finally {
        	// do for sure before leaving
        }
        return value;
    }
    // $ANTLR end "numericNegative"

    // Delegated rules


 

    public static final BitSet FOLLOW_directiveStatement_in_parse51 = new BitSet(new long[]{0x0060010000400200L,0x0000000000000010L});
    public static final BitSet FOLLOW_triplesStatement_in_parse60 = new BitSet(new long[]{0x0060010000400000L,0x0000000000000010L});
    public static final BitSet FOLLOW_triplesStatement_in_parse71 = new BitSet(new long[]{0x0060010000400000L,0x0000000000000010L});
    public static final BitSet FOLLOW_EOF_in_parse75 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_directive_in_directiveStatement90 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_PERIOD_in_directiveStatement92 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_triples_in_triplesStatement109 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_WS_in_triplesStatement111 = new BitSet(new long[]{0x0004000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_PERIOD_in_triplesStatement114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_base_in_directive129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_prefixID_in_directive135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_base148 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_BASE_in_base150 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_uriref_in_base152 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_prefixID170 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_PREFIX_in_prefixID172 = new BitSet(new long[]{0x0000200000010000L});
    public static final BitSet FOLLOW_namespace_in_prefixID175 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_defaultNamespace_in_prefixID181 = new BitSet(new long[]{0x0000010000000000L});
    public static final BitSet FOLLOW_uriref_in_prefixID186 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subject_in_triples205 = new BitSet(new long[]{0x0020010000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_predicateObjectList_in_triples209 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_verb_in_predicateObjectList235 = new BitSet(new long[]{0x0060017043DC0000L,0x0000000000000058L});
    public static final BitSet FOLLOW_objectList_in_predicateObjectList239 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_SEMI_in_predicateObjectList249 = new BitSet(new long[]{0x0020010000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_verb_in_predicateObjectList253 = new BitSet(new long[]{0x0060017043DC0000L,0x0000000000000058L});
    public static final BitSet FOLLOW_objectList_in_predicateObjectList257 = new BitSet(new long[]{0x8000000000000002L});
    public static final BitSet FOLLOW_predicate_in_verb280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_75_in_verb288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_object_in_objectList314 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_objectList319 = new BitSet(new long[]{0x0060017043DC0000L,0x0000000000000058L});
    public static final BitSet FOLLOW_object_in_objectList323 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_resource_in_subject345 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_subject353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_subject361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_uriTemplateFunction_in_subject369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_predicate389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_object408 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_function_in_object416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_object424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_object433 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dataTypeFunction_in_object441 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_uriTemplateFunction_in_object449 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_uriref_in_resource469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_qname_in_resource477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_in_uriref496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_relativeURI_in_uriref498 = new BitSet(new long[]{0x0000000080000000L});
    public static final BitSet FOLLOW_GREATER_in_uriref500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PREFIXED_NAME_in_qname519 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_nodeID_in_blank534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLANK_in_blank540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_variable557 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_name_in_variable563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_resource_in_function582 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_LPAREN_in_function584 = new BitSet(new long[]{0x0060017043DC0000L,0x0000000000000048L});
    public static final BitSet FOLLOW_terms_in_function586 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_RPAREN_in_function588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_dataTypeFunction607 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_AT_in_dataTypeFunction609 = new BitSet(new long[]{0x0040000000400000L,0x0000000000000200L});
    public static final BitSet FOLLOW_language_in_dataTypeFunction611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_dataTypeFunction619 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_REFERENCE_in_dataTypeFunction621 = new BitSet(new long[]{0x0020010000000000L});
    public static final BitSet FOLLOW_resource_in_dataTypeFunction623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_languageTag_in_language642 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_language650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_WITH_TEMPLATE_SIGN_in_uriTemplateFunction674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_terms700 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_terms705 = new BitSet(new long[]{0x0060017043DC0000L,0x0000000000000048L});
    public static final BitSet FOLLOW_term_in_terms709 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_function_in_term730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variable_in_term738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_term746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_literal765 = new BitSet(new long[]{0x0000000000000202L});
    public static final BitSet FOLLOW_AT_in_literal768 = new BitSet(new long[]{0x0040000000400000L,0x0000000000000200L});
    public static final BitSet FOLLOW_language_in_literal770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dataTypeString_in_literal780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numericLiteral_in_literal788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_booleanLiteral_in_literal796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_WITH_QUOTE_DOUBLE_in_stringLiteral815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_stringLiteral_in_dataTypeString835 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_REFERENCE_in_dataTypeString837 = new BitSet(new long[]{0x0020010000000000L});
    public static final BitSet FOLLOW_resource_in_dataTypeString839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numericUnsigned_in_numericLiteral858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numericPositive_in_numericLiteral866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_numericNegative_in_numericLiteral874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BLANK_PREFIX_in_nodeID889 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_name_in_nodeID891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_URI_in_relativeURI904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAMESPACE_in_namespace917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_defaultNamespace932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VARNAME_in_name945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VARNAME_in_languageTag958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_booleanLiteral975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_booleanLiteral984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_in_numericUnsigned1003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_in_numericUnsigned1011 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_in_numericUnsigned1020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_POSITIVE_in_numericPositive1039 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_POSITIVE_in_numericPositive1047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_POSITIVE_in_numericPositive1056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_NEGATIVE_in_numericNegative1075 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_NEGATIVE_in_numericNegative1083 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DECIMAL_NEGATIVE_in_numericNegative1092 = new BitSet(new long[]{0x0000000000000002L});

}