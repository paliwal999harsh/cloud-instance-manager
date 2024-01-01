import { useState } from "react";

const AdvanceOptions  = () => {
    const [alwaysOn, setAlwaysOn] = useState(false);
    const [weekendOn,setWeekendOn] = useState(false);
    return (
        <div className="mt-10 space-y-10">
        <fieldset>
          <legend className="text-sm font-semibold leading-6 text-gray-900">Advance Options</legend>
          <div className="mt-6 space-y-6">
            <div className="relative flex gap-x-3">
              <div className="flex h-6 items-center">
                <input id="AlwaysOn" name="alwaysOn" type="checkbox" value={alwaysOn} onChange={(alwaysOn) => setAlwaysOn(alwaysOn)} className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600" />
              </div>
              <div className="text-sm leading-6">
                <label htmlFor="AlwaysOn" className="font-medium text-gray-900">Always On</label>
                <p className="text-gray-500">Keep running the instance 24 hours of the day.</p>
              </div>
            </div>
            <div className="relative flex gap-x-3">
              <div className="flex h-6 items-center">
                <input id="WeekendOn" name="weekendOn" type="checkbox" value={weekendOn} onChange={(weekendOn) => setWeekendOn(weekendOn)} className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600" />
              </div>
              <div className="text-sm leading-6">
                <label htmlFor="WeekendOn" className="font-medium text-gray-900">Weekend On</label>
                <p className="text-gray-500">Keep the instance running on weekends as well.</p>
              </div>
            </div>
          </div>
        </fieldset>
      </div>
    );
};

export default AdvanceOptions;