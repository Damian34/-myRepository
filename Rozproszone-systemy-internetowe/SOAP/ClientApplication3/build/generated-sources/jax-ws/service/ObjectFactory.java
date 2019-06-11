
package service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AllStatus_QNAME = new QName("http://Service/", "AllStatus");
    private final static QName _AllStatusResponse_QNAME = new QName("http://Service/", "AllStatusResponse");
    private final static QName _BookAdd_QNAME = new QName("http://Service/", "BookAdd");
    private final static QName _BookAddResponse_QNAME = new QName("http://Service/", "BookAddResponse");
    private final static QName _BookGive_QNAME = new QName("http://Service/", "BookGive");
    private final static QName _BookGiveResponse_QNAME = new QName("http://Service/", "BookGiveResponse");
    private final static QName _BookReturn_QNAME = new QName("http://Service/", "BookReturn");
    private final static QName _BookReturnResponse_QNAME = new QName("http://Service/", "BookReturnResponse");
    private final static QName _BooksAll_QNAME = new QName("http://Service/", "BooksAll");
    private final static QName _BooksAllResponse_QNAME = new QName("http://Service/", "BooksAllResponse");
    private final static QName _BooksAvailable_QNAME = new QName("http://Service/", "BooksAvailable");
    private final static QName _BooksAvailableResponse_QNAME = new QName("http://Service/", "BooksAvailableResponse");
    private final static QName _BooksBorrowed_QNAME = new QName("http://Service/", "BooksBorrowed");
    private final static QName _BooksBorrowedResponse_QNAME = new QName("http://Service/", "BooksBorrowedResponse");
    private final static QName _BooksHeld_QNAME = new QName("http://Service/", "BooksHeld");
    private final static QName _BooksHeldResponse_QNAME = new QName("http://Service/", "BooksHeldResponse");
    private final static QName _BorrowedStatus_QNAME = new QName("http://Service/", "BorrowedStatus");
    private final static QName _BorrowedStatusResponse_QNAME = new QName("http://Service/", "BorrowedStatusResponse");
    private final static QName _ReaderAdd_QNAME = new QName("http://Service/", "ReaderAdd");
    private final static QName _ReaderAddResponse_QNAME = new QName("http://Service/", "ReaderAddResponse");
    private final static QName _ReaderAll_QNAME = new QName("http://Service/", "ReaderAll");
    private final static QName _ReaderAllResponse_QNAME = new QName("http://Service/", "ReaderAllResponse");
    private final static QName _ReaderBorrower_QNAME = new QName("http://Service/", "ReaderBorrower");
    private final static QName _ReaderBorrowerResponse_QNAME = new QName("http://Service/", "ReaderBorrowerResponse");
    private final static QName _ReaderStatus_QNAME = new QName("http://Service/", "ReaderStatus");
    private final static QName _ReaderStatusResponse_QNAME = new QName("http://Service/", "ReaderStatusResponse");
    private final static QName _RemoveBook_QNAME = new QName("http://Service/", "RemoveBook");
    private final static QName _RemoveBookResponse_QNAME = new QName("http://Service/", "RemoveBookResponse");
    private final static QName _RemoveReader_QNAME = new QName("http://Service/", "RemoveReader");
    private final static QName _RemoveReaderResponse_QNAME = new QName("http://Service/", "RemoveReaderResponse");
    private final static QName _UpdateAll_QNAME = new QName("http://Service/", "UpdateAll");
    private final static QName _UpdateAllResponse_QNAME = new QName("http://Service/", "UpdateAllResponse");
    private final static QName _Czytelnik_QNAME = new QName("http://Service/", "czytelnik");
    private final static QName _Książka_QNAME = new QName("http://Service/", "ksi\u0105\u017cka");
    private final static QName _Wypożyczenie_QNAME = new QName("http://Service/", "wypo\u017cyczenie");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AllStatus }
     * 
     */
    public AllStatus createAllStatus() {
        return new AllStatus();
    }

    /**
     * Create an instance of {@link AllStatusResponse }
     * 
     */
    public AllStatusResponse createAllStatusResponse() {
        return new AllStatusResponse();
    }

    /**
     * Create an instance of {@link BookAdd }
     * 
     */
    public BookAdd createBookAdd() {
        return new BookAdd();
    }

    /**
     * Create an instance of {@link BookAddResponse }
     * 
     */
    public BookAddResponse createBookAddResponse() {
        return new BookAddResponse();
    }

    /**
     * Create an instance of {@link BookGive }
     * 
     */
    public BookGive createBookGive() {
        return new BookGive();
    }

    /**
     * Create an instance of {@link BookGiveResponse }
     * 
     */
    public BookGiveResponse createBookGiveResponse() {
        return new BookGiveResponse();
    }

    /**
     * Create an instance of {@link BookReturn }
     * 
     */
    public BookReturn createBookReturn() {
        return new BookReturn();
    }

    /**
     * Create an instance of {@link BookReturnResponse }
     * 
     */
    public BookReturnResponse createBookReturnResponse() {
        return new BookReturnResponse();
    }

    /**
     * Create an instance of {@link BooksAll }
     * 
     */
    public BooksAll createBooksAll() {
        return new BooksAll();
    }

    /**
     * Create an instance of {@link BooksAllResponse }
     * 
     */
    public BooksAllResponse createBooksAllResponse() {
        return new BooksAllResponse();
    }

    /**
     * Create an instance of {@link BooksAvailable }
     * 
     */
    public BooksAvailable createBooksAvailable() {
        return new BooksAvailable();
    }

    /**
     * Create an instance of {@link BooksAvailableResponse }
     * 
     */
    public BooksAvailableResponse createBooksAvailableResponse() {
        return new BooksAvailableResponse();
    }

    /**
     * Create an instance of {@link BooksBorrowed }
     * 
     */
    public BooksBorrowed createBooksBorrowed() {
        return new BooksBorrowed();
    }

    /**
     * Create an instance of {@link BooksBorrowedResponse }
     * 
     */
    public BooksBorrowedResponse createBooksBorrowedResponse() {
        return new BooksBorrowedResponse();
    }

    /**
     * Create an instance of {@link BooksHeld }
     * 
     */
    public BooksHeld createBooksHeld() {
        return new BooksHeld();
    }

    /**
     * Create an instance of {@link BooksHeldResponse }
     * 
     */
    public BooksHeldResponse createBooksHeldResponse() {
        return new BooksHeldResponse();
    }

    /**
     * Create an instance of {@link BorrowedStatus }
     * 
     */
    public BorrowedStatus createBorrowedStatus() {
        return new BorrowedStatus();
    }

    /**
     * Create an instance of {@link BorrowedStatusResponse }
     * 
     */
    public BorrowedStatusResponse createBorrowedStatusResponse() {
        return new BorrowedStatusResponse();
    }

    /**
     * Create an instance of {@link ReaderAdd }
     * 
     */
    public ReaderAdd createReaderAdd() {
        return new ReaderAdd();
    }

    /**
     * Create an instance of {@link ReaderAddResponse }
     * 
     */
    public ReaderAddResponse createReaderAddResponse() {
        return new ReaderAddResponse();
    }

    /**
     * Create an instance of {@link ReaderAll }
     * 
     */
    public ReaderAll createReaderAll() {
        return new ReaderAll();
    }

    /**
     * Create an instance of {@link ReaderAllResponse }
     * 
     */
    public ReaderAllResponse createReaderAllResponse() {
        return new ReaderAllResponse();
    }

    /**
     * Create an instance of {@link ReaderBorrower }
     * 
     */
    public ReaderBorrower createReaderBorrower() {
        return new ReaderBorrower();
    }

    /**
     * Create an instance of {@link ReaderBorrowerResponse }
     * 
     */
    public ReaderBorrowerResponse createReaderBorrowerResponse() {
        return new ReaderBorrowerResponse();
    }

    /**
     * Create an instance of {@link ReaderStatus }
     * 
     */
    public ReaderStatus createReaderStatus() {
        return new ReaderStatus();
    }

    /**
     * Create an instance of {@link ReaderStatusResponse }
     * 
     */
    public ReaderStatusResponse createReaderStatusResponse() {
        return new ReaderStatusResponse();
    }

    /**
     * Create an instance of {@link RemoveBook }
     * 
     */
    public RemoveBook createRemoveBook() {
        return new RemoveBook();
    }

    /**
     * Create an instance of {@link RemoveBookResponse }
     * 
     */
    public RemoveBookResponse createRemoveBookResponse() {
        return new RemoveBookResponse();
    }

    /**
     * Create an instance of {@link RemoveReader }
     * 
     */
    public RemoveReader createRemoveReader() {
        return new RemoveReader();
    }

    /**
     * Create an instance of {@link RemoveReaderResponse }
     * 
     */
    public RemoveReaderResponse createRemoveReaderResponse() {
        return new RemoveReaderResponse();
    }

    /**
     * Create an instance of {@link UpdateAll }
     * 
     */
    public UpdateAll createUpdateAll() {
        return new UpdateAll();
    }

    /**
     * Create an instance of {@link UpdateAllResponse }
     * 
     */
    public UpdateAllResponse createUpdateAllResponse() {
        return new UpdateAllResponse();
    }

    /**
     * Create an instance of {@link Czytelnik }
     * 
     */
    public Czytelnik createCzytelnik() {
        return new Czytelnik();
    }

    /**
     * Create an instance of {@link Książka }
     * 
     */
    public Książka createKsiążka() {
        return new Książka();
    }

    /**
     * Create an instance of {@link Wypożyczenie }
     * 
     */
    public Wypożyczenie createWypożyczenie() {
        return new Wypożyczenie();
    }

    /**
     * Create an instance of {@link Punkt2 }
     * 
     */
    public Punkt2 createPunkt2() {
        return new Punkt2();
    }

    /**
     * Create an instance of {@link Punkt4 }
     * 
     */
    public Punkt4 createPunkt4() {
        return new Punkt4();
    }

    /**
     * Create an instance of {@link Punkt3 }
     * 
     */
    public Punkt3 createPunkt3() {
        return new Punkt3();
    }

    /**
     * Create an instance of {@link Punkt1 }
     * 
     */
    public Punkt1 createPunkt1() {
        return new Punkt1();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "AllStatus")
    public JAXBElement<AllStatus> createAllStatus(AllStatus value) {
        return new JAXBElement<AllStatus>(_AllStatus_QNAME, AllStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AllStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "AllStatusResponse")
    public JAXBElement<AllStatusResponse> createAllStatusResponse(AllStatusResponse value) {
        return new JAXBElement<AllStatusResponse>(_AllStatusResponse_QNAME, AllStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookAdd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BookAdd")
    public JAXBElement<BookAdd> createBookAdd(BookAdd value) {
        return new JAXBElement<BookAdd>(_BookAdd_QNAME, BookAdd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookAddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BookAddResponse")
    public JAXBElement<BookAddResponse> createBookAddResponse(BookAddResponse value) {
        return new JAXBElement<BookAddResponse>(_BookAddResponse_QNAME, BookAddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookGive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BookGive")
    public JAXBElement<BookGive> createBookGive(BookGive value) {
        return new JAXBElement<BookGive>(_BookGive_QNAME, BookGive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookGiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BookGiveResponse")
    public JAXBElement<BookGiveResponse> createBookGiveResponse(BookGiveResponse value) {
        return new JAXBElement<BookGiveResponse>(_BookGiveResponse_QNAME, BookGiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookReturn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BookReturn")
    public JAXBElement<BookReturn> createBookReturn(BookReturn value) {
        return new JAXBElement<BookReturn>(_BookReturn_QNAME, BookReturn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BookReturnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BookReturnResponse")
    public JAXBElement<BookReturnResponse> createBookReturnResponse(BookReturnResponse value) {
        return new JAXBElement<BookReturnResponse>(_BookReturnResponse_QNAME, BookReturnResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksAll")
    public JAXBElement<BooksAll> createBooksAll(BooksAll value) {
        return new JAXBElement<BooksAll>(_BooksAll_QNAME, BooksAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksAllResponse")
    public JAXBElement<BooksAllResponse> createBooksAllResponse(BooksAllResponse value) {
        return new JAXBElement<BooksAllResponse>(_BooksAllResponse_QNAME, BooksAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksAvailable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksAvailable")
    public JAXBElement<BooksAvailable> createBooksAvailable(BooksAvailable value) {
        return new JAXBElement<BooksAvailable>(_BooksAvailable_QNAME, BooksAvailable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksAvailableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksAvailableResponse")
    public JAXBElement<BooksAvailableResponse> createBooksAvailableResponse(BooksAvailableResponse value) {
        return new JAXBElement<BooksAvailableResponse>(_BooksAvailableResponse_QNAME, BooksAvailableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksBorrowed }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksBorrowed")
    public JAXBElement<BooksBorrowed> createBooksBorrowed(BooksBorrowed value) {
        return new JAXBElement<BooksBorrowed>(_BooksBorrowed_QNAME, BooksBorrowed.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksBorrowedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksBorrowedResponse")
    public JAXBElement<BooksBorrowedResponse> createBooksBorrowedResponse(BooksBorrowedResponse value) {
        return new JAXBElement<BooksBorrowedResponse>(_BooksBorrowedResponse_QNAME, BooksBorrowedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksHeld }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksHeld")
    public JAXBElement<BooksHeld> createBooksHeld(BooksHeld value) {
        return new JAXBElement<BooksHeld>(_BooksHeld_QNAME, BooksHeld.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BooksHeldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BooksHeldResponse")
    public JAXBElement<BooksHeldResponse> createBooksHeldResponse(BooksHeldResponse value) {
        return new JAXBElement<BooksHeldResponse>(_BooksHeldResponse_QNAME, BooksHeldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrowedStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BorrowedStatus")
    public JAXBElement<BorrowedStatus> createBorrowedStatus(BorrowedStatus value) {
        return new JAXBElement<BorrowedStatus>(_BorrowedStatus_QNAME, BorrowedStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BorrowedStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "BorrowedStatusResponse")
    public JAXBElement<BorrowedStatusResponse> createBorrowedStatusResponse(BorrowedStatusResponse value) {
        return new JAXBElement<BorrowedStatusResponse>(_BorrowedStatusResponse_QNAME, BorrowedStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderAdd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderAdd")
    public JAXBElement<ReaderAdd> createReaderAdd(ReaderAdd value) {
        return new JAXBElement<ReaderAdd>(_ReaderAdd_QNAME, ReaderAdd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderAddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderAddResponse")
    public JAXBElement<ReaderAddResponse> createReaderAddResponse(ReaderAddResponse value) {
        return new JAXBElement<ReaderAddResponse>(_ReaderAddResponse_QNAME, ReaderAddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderAll")
    public JAXBElement<ReaderAll> createReaderAll(ReaderAll value) {
        return new JAXBElement<ReaderAll>(_ReaderAll_QNAME, ReaderAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderAllResponse")
    public JAXBElement<ReaderAllResponse> createReaderAllResponse(ReaderAllResponse value) {
        return new JAXBElement<ReaderAllResponse>(_ReaderAllResponse_QNAME, ReaderAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderBorrower }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderBorrower")
    public JAXBElement<ReaderBorrower> createReaderBorrower(ReaderBorrower value) {
        return new JAXBElement<ReaderBorrower>(_ReaderBorrower_QNAME, ReaderBorrower.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderBorrowerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderBorrowerResponse")
    public JAXBElement<ReaderBorrowerResponse> createReaderBorrowerResponse(ReaderBorrowerResponse value) {
        return new JAXBElement<ReaderBorrowerResponse>(_ReaderBorrowerResponse_QNAME, ReaderBorrowerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderStatus")
    public JAXBElement<ReaderStatus> createReaderStatus(ReaderStatus value) {
        return new JAXBElement<ReaderStatus>(_ReaderStatus_QNAME, ReaderStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReaderStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ReaderStatusResponse")
    public JAXBElement<ReaderStatusResponse> createReaderStatusResponse(ReaderStatusResponse value) {
        return new JAXBElement<ReaderStatusResponse>(_ReaderStatusResponse_QNAME, ReaderStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "RemoveBook")
    public JAXBElement<RemoveBook> createRemoveBook(RemoveBook value) {
        return new JAXBElement<RemoveBook>(_RemoveBook_QNAME, RemoveBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "RemoveBookResponse")
    public JAXBElement<RemoveBookResponse> createRemoveBookResponse(RemoveBookResponse value) {
        return new JAXBElement<RemoveBookResponse>(_RemoveBookResponse_QNAME, RemoveBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReader }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "RemoveReader")
    public JAXBElement<RemoveReader> createRemoveReader(RemoveReader value) {
        return new JAXBElement<RemoveReader>(_RemoveReader_QNAME, RemoveReader.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveReaderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "RemoveReaderResponse")
    public JAXBElement<RemoveReaderResponse> createRemoveReaderResponse(RemoveReaderResponse value) {
        return new JAXBElement<RemoveReaderResponse>(_RemoveReaderResponse_QNAME, RemoveReaderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "UpdateAll")
    public JAXBElement<UpdateAll> createUpdateAll(UpdateAll value) {
        return new JAXBElement<UpdateAll>(_UpdateAll_QNAME, UpdateAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "UpdateAllResponse")
    public JAXBElement<UpdateAllResponse> createUpdateAllResponse(UpdateAllResponse value) {
        return new JAXBElement<UpdateAllResponse>(_UpdateAllResponse_QNAME, UpdateAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Czytelnik }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "czytelnik")
    public JAXBElement<Czytelnik> createCzytelnik(Czytelnik value) {
        return new JAXBElement<Czytelnik>(_Czytelnik_QNAME, Czytelnik.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Książka }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "ksi\u0105\u017cka")
    public JAXBElement<Książka> createKsiążka(Książka value) {
        return new JAXBElement<Książka>(_Książka_QNAME, Książka.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Wypożyczenie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Service/", name = "wypo\u017cyczenie")
    public JAXBElement<Wypożyczenie> createWypożyczenie(Wypożyczenie value) {
        return new JAXBElement<Wypożyczenie>(_Wypożyczenie_QNAME, Wypożyczenie.class, null, value);
    }

}
