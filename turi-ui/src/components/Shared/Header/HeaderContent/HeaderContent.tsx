import { ReactNode } from 'react'
import HeaderLogo from '../HeaderLogo'
import styles from './HeaderContent.module.css'

interface Props {
    links?: ReactNode
    buttons?: ReactNode
}

const HeaderContent = ({ links, buttons }: Props) => {
    return (
        <div className={styles.wrapper}>
            <div className={styles.content}>
                <HeaderLogo />
                {links}
                {buttons}
            </div>
        </div>
    )
}

export default HeaderContent
