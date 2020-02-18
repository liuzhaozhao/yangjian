/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.statement;

import com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.SQLExpr;
import com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.SQLName;
import com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.SQLStatementImpl;
import com.yametech.yangjian.agent.plugin.mysql.druid.sql.visitor.SQLASTVisitor;

public class SQLDropTableSpaceStatement extends SQLStatementImpl implements SQLDropStatement {

    private SQLName name;
    private boolean ifExists;
    private SQLExpr engine;
    
    public SQLDropTableSpaceStatement() {
        
    }
    
    public SQLDropTableSpaceStatement(String dbType) {
        super (dbType);
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, name);
        }
        visitor.endVisit(this);
    }

    public SQLName getName() {
        return name;
    }

    public void setName(SQLName x) {
        if (x != null) {
            x.setParent(this);
        }
        this.name = x;
    }

    public SQLExpr getEngine() {
        return engine;
    }

    public void setEngine(SQLExpr x) {
        if (x != null) {
            x.setParent(this);
        }
        this.engine = x;
    }

    public boolean isIfExists() {
        return ifExists;
    }

    public void setIfExists(boolean ifExists) {
        this.ifExists = ifExists;
    }

}
