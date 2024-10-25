import React from 'react'
import Input from '../../Controls/Input'
import styles from './AuthInput.module.css'

interface Props {
    type: string
    name: string
    placeholder: string
    value: string
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    minLength?: number
    maxLength?: number
    required: boolean
}

const AuthInput = ({ type, name, placeholder, value, onChange, minLength, maxLength, required }: Props) => {
    return (
        <div className={styles.input}>
            <Input
                type={type}
                name={name}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                minLength={minLength}
                maxLength={maxLength}
                required={required}
            />
        </div>
    )
}

export default AuthInput
