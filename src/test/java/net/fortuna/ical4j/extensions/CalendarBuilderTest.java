/**
 * Copyright (c) 2009, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.extensions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarParser;
import net.fortuna.ical4j.data.CalendarParserFactory;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.extensions.property.MSOlkOriginalEnd;
import net.fortuna.ical4j.extensions.property.MSOlkOriginalStart;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ParameterFactoryRegistry;
import net.fortuna.ical4j.model.PropertyFactoryRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * @author Ben
 *
 */
@RunWith(Parameterized.class)
public class CalendarBuilderTest {

    private CalendarBuilder builder;
    
    private String filename;
    
    private boolean expectedValid;
    
    public CalendarBuilderTest(String filename, boolean expectedValid) {
        this.filename = filename;
        this.expectedValid = expectedValid;
    }
    
    @Before
    public void setUp() {
        CalendarParser parser = CalendarParserFactory.getInstance().createParser();
        
        PropertyFactoryRegistry propertyFactoryRegistry = new PropertyFactoryRegistry();
        propertyFactoryRegistry.register(MSOlkOriginalStart.PROPERTY_NAME, MSOlkOriginalStart.FACTORY);
        propertyFactoryRegistry.register(MSOlkOriginalEnd.PROPERTY_NAME, MSOlkOriginalEnd.FACTORY);
        
        ParameterFactoryRegistry parameterFactoryRegistry = new ParameterFactoryRegistry();
        
        TimeZoneRegistry tzRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();
        
        builder = new CalendarBuilder(parser, propertyFactoryRegistry, parameterFactoryRegistry, tzRegistry);
    }
    
    @Test
    public void testBuild() throws FileNotFoundException, IOException, ParserException, ValidationException {
        Calendar calendar = builder.build(getClass().getResourceAsStream(filename));
        try {
            calendar.validate();
            if (!expectedValid) {
                Assert.fail("Expected invalid calendar");
            }
        }
        catch (ValidationException e) {
            if (expectedValid) {
                Assert.fail(String.format("Expected valid calendar: %s", e.getMessage()));
            }
        }
    }
    
    @Parameters
    public static Collection<Object[]> parameters() {
        List<Object[]> params = new ArrayList<Object[]>();
        params.add(new Object[] { "/samples/Bodypart2.ics", true });
        return params;
    }
}