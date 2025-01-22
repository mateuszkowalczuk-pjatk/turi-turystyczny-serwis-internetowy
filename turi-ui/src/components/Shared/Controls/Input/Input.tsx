import React from 'react'
import './Input.module.css'

interface Props {
    type: string
    name: string
    placeholder: string
    value: string | number | Date | undefined | null
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    minLength?: number
    maxLength?: number
    min?: string
    max?: string
    required: boolean
    disabled?: boolean
}

const Input = ({ type, name, placeholder, value, onChange, minLength, maxLength, min, max, required, disabled }: Props) => {
    const formatValue = (value: string | number | Date | undefined | null): string | number | undefined => {
        if (value instanceof Date) return value.toISOString().split('T')[0]
        return value !== null ? value : ''
    }

    return (
        <input
            type={type}
            name={name}
            placeholder={placeholder}
            value={formatValue(value)}
            onChange={onChange}
            minLength={minLength}
            maxLength={maxLength}
            min={min}
            max={max}
            required={required}
            disabled={disabled}
            autoComplete="off"
        />
    )
}

export default Input
