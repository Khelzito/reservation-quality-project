package booking;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ReservationServiceMockitoTest.class, ReservationDomainTest.class, ReservationParameterizedTest.class})
public class ReservationTestSuite {
}
