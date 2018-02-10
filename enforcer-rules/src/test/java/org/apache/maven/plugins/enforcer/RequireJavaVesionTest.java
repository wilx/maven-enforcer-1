package org.apache.maven.plugins.enforcer;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * The Class RequireJavaVersionTest
 *
 * @author Karl Heinz Marbaise <a href="mailto:khmarbaise@apache.org">khmarbaise@apache.org</a>
 */
@RunWith( PowerMockRunner.class )
@PrepareForTest( { SystemUtils.class } )
public class RequireJavaVesionTest
{

    private EnforcerRuleHelper helper;

    private RequireJavaVersion rule;

    @Before
    public void before()
    {

        helper = mock( EnforcerRuleHelper.class );

        Log log = mock( Log.class );
        when( helper.getLog() ).thenReturn( log );

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void first()
        throws EnforcerRuleException
    {
        Whitebox.setInternalState( SystemUtils.class, "JAVA_VERSION", "1.4" );

        rule = new RequireJavaVersion();
        rule.setVersion( "1.5" );

        exception.expect( EnforcerRuleException.class );
        exception.expectMessage( "Detected JDK Version: 1.4 is not in the allowed range 1.5." );
        rule.execute( helper );
    }

    @Test
    public void second()
        throws EnforcerRuleException
    {
        Whitebox.setInternalState( SystemUtils.class, "JAVA_VERSION", "1.8" );

        rule = new RequireJavaVersion();
        rule.setVersion( "1.9" );
        exception.expect( EnforcerRuleException.class );
        exception.expectMessage( "Detected JDK Version: 1.8 is not in the allowed range 1.9." );
        rule.execute( helper );
    }

    @Test
    public void third()
        throws EnforcerRuleException
    {
        Whitebox.setInternalState( SystemUtils.class, "JAVA_VERSION", "9.0.4" );

        rule = new RequireJavaVersion();
        rule.setVersion( "9.0.5" );
        exception.expect( EnforcerRuleException.class );
        exception.expectMessage( "Detected JDK Version: 9.0.4 is not in the allowed range 9.0.5." );
        rule.execute( helper );
    }
    
    @Test
    public void forth()
        throws EnforcerRuleException
    {
        Whitebox.setInternalState( SystemUtils.class, "JAVA_VERSION", "1.8.0_b192" );

        rule = new RequireJavaVersion();
        rule.setVersion( "8" );
        exception.expect( EnforcerRuleException.class );
        exception.expectMessage( "Detected JDK Version: 9.0.4 is not in the allowed range 9.0.5." );
        rule.execute( helper );
    }

}
