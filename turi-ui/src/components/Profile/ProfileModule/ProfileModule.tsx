import ProfileLabel from '../ProfileLabel'
import ProfileInput from '../ProfileInput'
import styles from './ProfileModule.module.css'
import React from 'react'

interface Props {
    text: string
    type: string
    name: string
    placeholder: string
    value: string | number | undefined
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
    minLength?: number
    maxLength?: number
    required: boolean
    disabled?: boolean
}

const ProfileModule = ({
    text,
    type,
    name,
    placeholder,
    value,
    onChange,
    minLength,
    maxLength,
    required,
    disabled
}: Props) => {
    return (
        <div className={styles.module}>
            <ProfileLabel text={text} />
            <ProfileInput
                type={type}
                name={name}
                placeholder={placeholder}
                value={value}
                onChange={onChange}
                minLength={minLength}
                maxLength={maxLength}
                required={required}
                disabled={disabled}
            />
        </div>
    )
}

export default ProfileModule
