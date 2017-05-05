package com.hello.pattern;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hello.droid.BaseListActivity;
import com.hello.droid.Info;
import com.hello.pattern.adapter.Phone;
import com.hello.pattern.adapter.XiaoMi;
import com.hello.pattern.adapter.XiaoMiWrapper;
import com.hello.pattern.builder.Builder;
import com.hello.pattern.builder.ConcreteBuilder;
import com.hello.pattern.builder.Director;
import com.hello.pattern.command.TurnOffCommand;
import com.hello.pattern.command.TurnOnCommand;
import com.hello.pattern.command.Tv;
import com.hello.pattern.command.TvCommandInvoker;
import com.hello.pattern.decorator.Beverage;
import com.hello.pattern.decorator.EspressoCoffee;
import com.hello.pattern.decorator.Mocha;
import com.hello.pattern.decorator.Soy;
import com.hello.pattern.facade.DrawerOne;
import com.hello.pattern.facade.DrawerTwo;
import com.hello.pattern.facade.Facade;
import com.hello.pattern.factory.AbstracFactoryBMW320;
import com.hello.pattern.factory.BMW;
import com.hello.pattern.factory.BMW320Factory;
import com.hello.pattern.factory.BMW523Factory;
import com.hello.pattern.factory.BMWFactory;
import com.hello.pattern.factory.SimpleFactory;
import com.hello.pattern.singleton.LazySingleton;
import com.hello.pattern.strategy.AddStrategy;
import com.hello.pattern.strategy.StragetyController;
import com.hello.pattern.strategy.SubStrategy;

import java.util.ArrayList;
import java.util.List;

public class PatternTestActivity extends BaseListActivity {
    private static final String TAG = PatternTestActivity.class.getSimpleName();

    private enum DataItems {
        SINGLETON, STRATEGY, COMPOSE, OBSERVER, DECORATOR, FACTORY, COMMAND, FACADE,
        ADAPTER, BUILDER
    }

    @Override
    protected List<Info> buildDatas() {
        List<Info> datas = new ArrayList<Info>();
        for (DataItems e : DataItems.values()) {
            Info info = new Info(e.toString(), "");
            datas.add(info);
        }
        return datas;
    }

    @Override
    protected void onListItemClick(ListView listView, View v, int position, long id) {
        Log.d(TAG, "onListItemClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            case SINGLETON:
                LazySingleton.getInstance1().sayHello();
                break;
            case STRATEGY:
                // http://www.cnblogs.com/mengdd/archive/2013/01/19/2867443.html
                StragetyController env = new StragetyController(new AddStrategy());
                env.calculate(5, 8);
                env.setStrategy(new SubStrategy());
                env.calculate(8, 6);
                break;
            case OBSERVER:
                //testWatcherMode();
                break;
            case DECORATOR:
                Beverage decorator = new EspressoCoffee();
                decorator = new Mocha(decorator);
                decorator = new Mocha(decorator);
                decorator = new Soy(decorator);
                Log.d(TAG, "Coffee description:" + decorator.getDescription() + " and cost" + decorator.cost());

                break;
            case FACTORY:
                SimpleFactory simpleFactory = new SimpleFactory();
                simpleFactory.createBMW(BMW.BMW_320);
                simpleFactory.createBMW(BMW.BMW_523);
                BMWFactory bmwFactory = new BMW320Factory();
                bmwFactory.create();
                bmwFactory = new BMW523Factory();
                bmwFactory.create();

                AbstracFactoryBMW320 abstracFactoryBMW320 = new AbstracFactoryBMW320();
                abstracFactoryBMW320.createBMW();
                abstracFactoryBMW320.createAirCondition();
                break;
            case COMMAND:
                Tv tv = new Tv();
                TurnOnCommand turnOnCommand = new TurnOnCommand(tv);
                TurnOffCommand turnOffCommand = new TurnOffCommand(tv);
                TvCommandInvoker invoker = new TvCommandInvoker(turnOnCommand, turnOffCommand);
                invoker.TurnOn();
                break;
            case FACADE:
                Facade facade = new Facade(new DrawerOne(), new DrawerTwo());
                facade.getFile();
            case ADAPTER:
                XiaoMi xiaoMi = new XiaoMi();
                xiaoMi.call();
                xiaoMi.store();

                XiaoMiWrapper wrapper = new XiaoMiWrapper(new Phone());
                wrapper.store();
                wrapper.takeAlong();
                break;
            case BUILDER:
                Builder builder = new ConcreteBuilder();
                Director director = new Director(builder);
                ((ConcreteBuilder) builder).getProductor();

                break;

            default:
                break;
        }
        super.onListItemClick(listView, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemLongClick " + mHeaderAdapter.getData().get(position).getTitle());
        switch (DataItems.valueOf(mHeaderAdapter.getData().get(position).getTitle())) {
            default:
                break;
        }
        //return true and will not deal with short click event
        return true;
    }
}
