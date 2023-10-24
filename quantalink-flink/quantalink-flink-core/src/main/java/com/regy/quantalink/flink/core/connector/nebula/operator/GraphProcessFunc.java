package com.regy.quantalink.flink.core.connector.nebula.operator;

import com.regy.quantalink.common.utils.CopyUtils;
import com.regy.quantalink.flink.core.connector.ConnectorKey;
import com.regy.quantalink.flink.core.connector.SinkConnector;

import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import java.util.Map;

/**
 * @author regy
 */
public abstract class GraphProcessFunc<IN> extends ProcessFunction<IN, Void> {
    protected final Map<ConnectorKey<?>, SinkConnector<?, ?>> connectorMap;

    public GraphProcessFunc(Map<ConnectorKey<?>, SinkConnector<?, ?>> connectorMap) {
        this.connectorMap = CopyUtils.deepCopy(connectorMap);
    }

    @Override
    public void processElement(IN in, ProcessFunction<IN, Void>.Context ctx, Collector<Void> collector) {
        try {
            processGraph(in, ctx, collector, connectorMap);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process graph element: ", e);
        }
    }

    protected abstract void processGraph(
            IN in,
            ProcessFunction<IN, Void>.Context ctx,
            Collector<Void> collector,
            Map<ConnectorKey<?>, SinkConnector<?, ?>> connectorMap) throws Exception;
}
