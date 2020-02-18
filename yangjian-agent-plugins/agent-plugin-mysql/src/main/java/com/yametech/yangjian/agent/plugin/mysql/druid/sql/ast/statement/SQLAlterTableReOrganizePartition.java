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

import java.util.ArrayList;
import java.util.List;

import com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.SQLName;
import com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.SQLObject;
import com.yametech.yangjian.agent.plugin.mysql.druid.sql.ast.SQLObjectImpl;
import com.yametech.yangjian.agent.plugin.mysql.druid.sql.visitor.SQLASTVisitor;

public class SQLAlterTableReOrganizePartition extends SQLObjectImpl implements SQLAlterTableItem {

    private final List<SQLName>   names       = new ArrayList<SQLName>();

    private final List<SQLObject> partitions  = new ArrayList<SQLObject>(4);

    public List<SQLObject> getPartitions() {
        return partitions;
    }

    public void addPartition(SQLObject partition) {
        if (partition != null) {
            partition.setParent(this);
        }
        this.partitions.add(partition);
    }

    public List<SQLName> getNames() {
        return names;
    }

    @Override
    protected void accept0(SQLASTVisitor visitor) {
        if (visitor.visit(this)) {
            acceptChild(visitor, partitions);
        }
        visitor.endVisit(this);
    }
}
