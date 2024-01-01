import React, { useState } from 'react';

const TimeRangeSelector = ({ startTime, endTime, onChange }) => {
  const handleTimeChange = (newStartTime, newEndTime) => {
    onChange(newStartTime, newEndTime);
  };
    return (
      <div className="sm:col-span-3">
        <label htmlFor="time" className="mt-2 block text-sm font-medium leading-6 text-gray-900">Select Time Period</label>
        <div className='flex items-center mt-1'>
          <div className="relative">
            <input name="startTime" type="text" value={startTime} onChange={(time) => handleTimeChange(time,endTime)}  className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Enter Start Time (HH:MM)" />
          </div>
          <span className="mx-4 text-gray-500">to</span>
          <div className="relative">
            <input name="endTime" type="text" value={endTime} onChange={(time) => handleTimeChange(startTime,time)} className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Enter End Time (HH:MM)" />
          </div>
        </div>
      </div>
    );
  };
  
  export default TimeRangeSelector;