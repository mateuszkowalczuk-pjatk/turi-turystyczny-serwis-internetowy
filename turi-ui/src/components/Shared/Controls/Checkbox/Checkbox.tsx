import { ReactNode } from 'react'
import TextRegular from '../Text/TextRegular'
import styles from './Checkbox.module.css'

interface Props {
    checked: boolean
    onChange: () => void
    text?: string
    textPolicy?: ReactNode
}

const Checkbox = ({ checked, onChange, text, textPolicy }: Props) => {
    return (
        <label className={styles.checkbox}>
            <input
                checked={checked}
                onChange={onChange}
                type="checkbox"
            />
            <span className={styles.custom}></span>
            {text ? <TextRegular text={text} /> : <span>{textPolicy}</span>}
        </label>
    )
}

export default Checkbox
