import TextRegular from '../Text/TextRegular'
import styles from './Checkbox.module.css'

interface Props {
    checked: boolean;
    onChange: () => void;
    text: string;
}

const Checkbox = ({ checked, onChange, text }: Props) => {
    return (
        // eslint-disable-next-line jsx-a11y/label-has-associated-control
        <label className={styles.checkbox}>
            <input
                checked={checked}
                onChange={onChange}
                type="checkbox"
            />
            <span className={styles.custom}></span>
            <TextRegular
                text={ text }
            />
        </label>
    )
}

export default Checkbox;