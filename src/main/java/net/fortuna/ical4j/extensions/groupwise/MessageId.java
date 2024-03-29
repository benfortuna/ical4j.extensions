/**
 * Copyright (c) 2011, Ben Fortuna
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
package net.fortuna.ical4j.extensions.groupwise;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.util.ParameterValidator;

/**
 * @author fortuna
 *
 */
public class MessageId extends Property {

    private static final long serialVersionUID = -7910360817210293089L;

    public static final String PROPERTY_NAME = "X-GWMESSAGEID";
    
    public static final PropertyFactory FACTORY = new Factory();
    
    private String value;
    
    /**
     * @param factory
     */
    public MessageId(PropertyFactory factory) {
        super(PROPERTY_NAME, factory);
    }

    /**
     * @param aList
     * @param factory
     */
    public MessageId(ParameterList aList, PropertyFactory factory, String value) {
        super(PROPERTY_NAME, aList, factory);
        setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String aValue) {
        this.value = aValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ValidationException {
        ParameterValidator.getInstance().assertOneOrLess(Parameter.VALUE,
                getParameters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {
        return value;
    }

    private static class Factory implements PropertyFactory {

        private static final long serialVersionUID = 1L;

        public Property createProperty(String name) {
            return new MessageId(this);
        }
        
        public Property createProperty(String name, ParameterList parameters, String value) {
            MessageId property = new MessageId(parameters, this, value);
            return property;
        }
    }
}
