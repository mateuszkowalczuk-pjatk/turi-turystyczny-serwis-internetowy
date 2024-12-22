import { ReactNode } from 'react'
import TextMedium from '../../Shared/Controls/Text/TextMedium'
import TextRegular from '../../Shared/Controls/Text/TextRegular'
import styles from './PremiumVerify.module.css'

interface Props {
    text: string
    firstInputText: string
    firstInput?: ReactNode
    secondInputText: string
    secondInputDescription?: string
    secondInput?: ReactNode
}

const PremiumVerify = ({
    text,
    firstInputText,
    firstInput,
    secondInputText,
    secondInputDescription,
    secondInput
}: Props) => {
    return (
        <div className={styles.verify}>
            <TextMedium text={text} />
            <TextRegular text={firstInputText} />
            {firstInput}
            <TextRegular text={secondInputText} />
            {secondInputDescription && <p className={styles.bank}> {secondInputDescription} </p>}
            {secondInput}
        </div>
    )
}

export default PremiumVerify
