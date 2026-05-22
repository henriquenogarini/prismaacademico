import React from 'react';
import { cn } from '../../utils/cn';

interface TextareaProps extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {
  label?: string;
  error?: string;
  helperText?: string;
}

export default function Textarea({
  label,
  error,
  helperText,
  className,
  id,
  ...props
}: TextareaProps) {
  const textareaId = id ?? `textarea-${Math.random().toString(36).slice(2)}`;

  return (
    <div className="w-full">
      {label && (
        <label
          htmlFor={textareaId}
          className="block text-sm font-medium text-prismaDark-700 mb-1.5"
        >
          {label}
          {props.required && <span className="text-red-500 ml-1" aria-hidden="true">*</span>}
        </label>
      )}
      <textarea
        id={textareaId}
        rows={4}
        className={cn(
          'w-full px-3 py-2.5 border rounded-xl text-sm text-prismaDark-800',
          'placeholder:text-prismaGray-400 resize-none',
          'focus:outline-none focus:ring-2 focus:border-transparent transition-all duration-200',
          error
            ? 'border-red-400 focus:ring-red-400'
            : 'border-prismaGray-300 focus:ring-prismaBlue-500',
          className
        )}
        aria-invalid={!!error}
        {...props}
      />
      {error && (
        <p className="mt-1.5 text-xs text-red-600" role="alert">
          {error}
        </p>
      )}
      {helperText && !error && (
        <p className="mt-1.5 text-xs text-prismaGray-500">{helperText}</p>
      )}
    </div>
  );
}
