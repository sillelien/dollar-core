package com.sillelien.dollar.api.types;

import com.sillelien.dollar.api.DollarStatic;
import com.sillelien.dollar.api.Pipeable;
import com.sillelien.dollar.api.Type;
import com.sillelien.dollar.api.collections.ImmutableList;
import com.sillelien.dollar.api.collections.ImmutableMap;
import com.sillelien.dollar.api.guard.ChainGuard;
import com.sillelien.dollar.api.guard.Guarded;
import com.sillelien.dollar.api.guard.NotNullParametersGuard;
import com.sillelien.dollar.api.var;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class DollarQueue extends AbstractDollar implements var {

    private final ConcurrentHashMap<String, Pipeable> listeners = new ConcurrentHashMap<>();
    private Queue<var> queue;

    public DollarQueue(@NotNull ImmutableList<Throwable> errors, Queue<var> queue) {
        super(errors);
        this.queue = queue;
    }

    /**
     * Generic read
     */
    @Guarded(NotNullParametersGuard.class)
    @Guarded(ChainGuard.class)
    public var $read(boolean blocking, boolean mutating) {
        if (mutating) {
            return queue.poll();
        } else {
            return queue.peek();
        }
    }

    @Override
    public var $write(var value, boolean blocking, boolean mutating) {
        if (mutating) {
            queue.add(value);
            listeners.forEach((s, pipeable) -> {
                try {
                    pipeable.pipe(value);
                } catch (Exception e) {
                    DollarFactory.failure(e);
                }
            });
            return this;
        } else {
            return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
        }
    }

    @NotNull
    @Override
    public var $all() {
        return $list();
    }

    @Override
    public var $drain() {
        ArrayList<var> result = new ArrayList<>();
        while (queue.size() > 0) {
            result.add(queue.poll());
        }
        return DollarFactory.fromList(result);
    }

    @Override
    public var $publish(var value) {
        listeners.forEach((s, pipeable) -> {
            try {
                pipeable.pipe(value);
            } catch (Exception e) {
                DollarFactory.failure(e);
            }
        });
        return this;
    }

    @Override
    public var $each(@NotNull Pipeable pipe) {
        ArrayList<var> result = new ArrayList<>();
        while (queue.size() > 0) {
            try {
                result.add(pipe.pipe(queue.poll()));
            } catch (Exception e) {
                DollarFactory.failure(e);
            }
        }
        return DollarFactory.fromList(result);
    }

    @Override
    public var $listen(Pipeable pipeable, String id) {
        listeners.put(id, pipeable);
        return this;
    }


    @Override
    public String toHumanString() {
        return toJsonString();
    }

    @NotNull
    @Override
    public String toDollarScript() {
        DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
        return null;
    }

    @Nullable
    @Override
    public <R> R toJavaObject() {
        return (R) queue;
    }

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isFalse() {
        return false;
    }

    @Override
    public boolean isTrue() {
        return false;
    }

    @Override
    public boolean neitherTrueNorFalse() {
        return true;
    }

    @Override
    public boolean truthy() {
        return false;
    }

    @NotNull
    @Override
    public var $get(@NotNull var rhs) {
        return (var) queue.toArray()[rhs.toInteger()];
    }

    @NotNull
    @Override
    public var $append(@NotNull var value) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $containsValue(@NotNull var value) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $containsKey(@NotNull var value) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $has(@NotNull var key) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $size() {
        return DollarStatic.$(queue.size());
    }

    @NotNull
    @Override
    public var $prepend(@NotNull var value) {
        $push(value);
        return this;
    }

    @NotNull
    @Override
    public var $insert(@NotNull var value, int position) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $removeByKey(@NotNull String key) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $set(@NotNull var key, @Nullable Object value) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $remove(var valueToRemove) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public int compareTo(var o) {
        return 0;
    }

    @NotNull
    @Override
    public var $abs() {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $plus(@NotNull var rhs) {
        return $push(rhs);
    }

    @NotNull
    @Override
    public var $negate() {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $divide(@NotNull var rhs) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $modulus(@NotNull var rhs) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public var $multiply(@NotNull var v) {
        return DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
    }

    @NotNull
    @Override
    public Integer toInteger() {
        DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
        return null;
    }

    @Override
    public var $as(Type type) {
        if (type == Type.LIST) {
            return DollarStatic.$(queue.toArray());
        } else if (type == Type.MAP) {
            return DollarStatic.$(queue.toArray()).$map();
        }
        return DollarFactory.failure(ErrorType.INVALID_CAST);
    }

    @NotNull
    @Override
    public ImmutableList<var> toVarList() {
        return ImmutableList.copyOf(Arrays.asList((var[]) queue.toArray()));
    }

    @Override
    public Type $type() {
        return Type.QUEUE;
    }

    @NotNull
    @Override
    public ImmutableMap<var, var> toVarMap() {
        return DollarStatic.$(queue.toArray()).toVarMap();
    }

    @NotNull
    @Override
    public String toYaml() {
        Yaml yaml = new Yaml();
        return yaml.dump(queue.toArray());
    }

    @Override
    public boolean is(@NotNull Type... types) {
        for (Type type : types) {
            if (Objects.equals(type, Type.QUEUE)) {
                return true;
            }
        }
        return false;
    }


    @Nullable
    @Override
    public ImmutableList<String> strings() {
        return ImmutableList.copyOf(queue.stream().map(var::toString).collect(Collectors.toList()));
    }

    @NotNull
    @Override
    public ImmutableList<?> toList() {
        return ImmutableList.copyOf(Arrays.asList(queue.toArray()));
    }

    @NotNull
    @Override
    public <K extends Comparable<K>, V> ImmutableMap<K, V> toJavaMap() {
        DollarFactory.failure(ErrorType.INVALID_QUEUE_OPERATION);
        return null;
    }

    @Override
    public boolean queue() {
        return true;
    }
}
