import React, { useState } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const SVGIcon = () => (
    <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
        <svg className="w-4 h-4 text-gray-500 text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
        <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
        </svg>
    </div>
  );

const DateRangePicker =({ startDate, endDate, onChange }) => {

    const handleDateRangeChange = (newStartDate, newEndDate) => {
      // Invoke the onChange prop with the selected date range
      if (onChange) {
        onChange(newStartDate, newEndDate);
      }
    };
  
  return (
    <div className="sm:col-span-3">
        <label htmlFor="date" className="mt-2 block text-sm font-medium leading-6 text-gray-900">Select Date Range</label>
        <div className="flex items-center mt-1">
            <div className='relative'> 
                <SVGIcon />
                <DatePicker
                    selected={startDate}
                    onChange={(date) => handleDateRangeChange(date, endDate)}
                    selectsStart
                    startDate={startDate}
                    endDate={endDate}
                    placeholderText="Start Date"
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5"
                />
            </div>
            <span className="mx-4 text-gray-500">to</span>
            <div className='relative'>
                <SVGIcon />
                <DatePicker
                    selected={endDate}
                    onChange={(date) => handleDateRangeChange(startDate, date)}
                    selectsEnd
                    startDate={startDate}
                    endDate={endDate}
                    minDate={startDate}
                    placeholderText="End Date"
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full ps-10 p-2.5"
                />
            </div>
        </div>
    </div>
  );
};

export default DateRangePicker;